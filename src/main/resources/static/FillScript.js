$(async function () {
    let url = '/admin-rest';
    let response = await fetch(url);
    let users = await response.json();
    let allRolesResponse = await fetch('/admin-rest/roles')
    let allRoles = await allRolesResponse.json();
    let body = $("#users-table tbody");
    users.forEach(function (user, index) {
        let tr = $('<tr>');
        tr.append($('<td>').attr('id','id' + index.toString()).text(user.id));
        tr.append($('<td>').attr('id','name' + index.toString()).text(user.name));
        tr.append($('<td>').attr('id','lastName' + index.toString()).text(user.lastName));
        tr.append($('<td>').attr('id','age' + index.toString()).text(user.age));
        tr.append($('<td>').attr('id','username' + index.toString()).text(user.username));
        let roleText = '';
        user.roles.forEach(function (role){
            roleText = roleText + role.authority + ' ';
        })
        tr.append($('<td>').attr('id','roles' + index.toString()).text(roleText));
        tr.append($('<td>').append($('<button>').attr('type','button')
            .attr('class','btn btn-primary').attr('data-toggle','modal')
            .attr('data-target','#editModal').attr('data-whatever',index).html('Edit')));
        tr.append($('<td>').append($('<button>').attr('type','button')
            .attr('class','btn btn-danger').attr('data-toggle','modal')
            .attr('data-target','#deleteModal').attr('data-whatever',index).html('Delete')));
        body.prepend(tr.attr('id','row' + index.toString()));
    })


    $('#editModal').on('shown.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let recipient = button.data('whatever');
        let modal = $(this);

        let oldRolesInput = modal.find('#oldRoles');
        let oldRoles = [];
        users[recipient].roles.forEach(function (role){
            oldRoles.push(role.id);
        })
        oldRolesInput.val(oldRoles);

        modal.find('#idModalJS').val(users[recipient].id);
        modal.find('#nameModalJS').val(users[recipient].name);
        modal.find('#lastNameModalJS').val(users[recipient].lastName);
        modal.find('#ageModalJS').val(users[recipient].age);
        modal.find('#usernameModalJS').val(users[recipient].username);
        modal.find('#passwordModalJS').val(users[recipient].password);
        allRoles.forEach(function (role) {
            modal.find('#rolesModalJS').append($('<option>')
                .attr('value',role.id).html(role.authority))
        })
        editForm.onsubmit = async (e) => {
            e.preventDefault();
            let newRoles = [];
            Array.from($('#rolesModalJS').val()).forEach(function (roleid) {
                newRoles.push(allRoles.find(role => role.id === parseInt(roleid)))
            })
            let user = {
                id: editForm.elements['idModalJS'].value,
                name: editForm.elements['nameModalJS'].value,
                lastName: editForm.elements['lastNameModalJS'].value,
                age: editForm.elements['ageModalJS'].value,
                roles: newRoles,
                password: editForm.elements['passwordModalJS'].value,
                username: editForm.elements['usernameModalJS'].value,
            }
            let response = await fetch('/admin-rest', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8',
                },
                body: JSON.stringify(user)

            });

            let result = await response.json();
            $("#id"+recipient.toString()).text(result.id);
            $("#name"+recipient.toString()).text(result.name);
            $("#lastName"+recipient.toString()).text(result.lastName);
            $("#age"+recipient.toString()).text(result.age);
            $("#username"+recipient.toString()).text(result.username);
            let roleTextResponse = '';
            result.roles.forEach(function (role){
                roleTextResponse = roleTextResponse + role.authority + ' ';
            })
            $("#roles"+recipient.toString()).text(roleTextResponse);

            users[recipient].id = result.id;
            users[recipient].name = result.name;
            users[recipient].lastName = result.lastName;
            users[recipient].age = result.age;
            users[recipient].username = result.username;
            users[recipient].password = result.password;
        }
    })

    allRoles.forEach(function (role) {
        $('#roles').append($('<option>')
            .attr('value', role.id).html(role.authority))
    })

    newuserForm.onsubmit = async (e) => {
        e.preventDefault();
        let newRoles = [];
        Array.from($('#roles').val()).forEach(function (roleid) {
            newRoles.push(allRoles.find(role => role.id === parseInt(roleid)))
        })
        let user = {
            name: newuserForm.elements['name'].value,
            lastName: newuserForm.elements['lastName'].value,
            age: newuserForm.elements['age'].value,
            roles: newRoles,
            password: newuserForm.elements['password'].value,
            username: newuserForm.elements['username'].value,
        }
        let response = await fetch('/admin-rest', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
            },
            body: JSON.stringify(user)

        });

        let result = await response.json();
        users.push(result);
        body.children("tr").remove();

        users.forEach(function (user, index) {
            let tr = $('<tr>');
            tr.append($('<td>').attr('id','id' + index.toString()).text(user.id));
            tr.append($('<td>').attr('id','name' + index.toString()).text(user.name));
            tr.append($('<td>').attr('id','lastName' + index.toString()).text(user.lastName));
            tr.append($('<td>').attr('id','age' + index.toString()).text(user.age));
            tr.append($('<td>').attr('id','username' + index.toString()).text(user.username));
            let roleText = '';
            user.roles.forEach(function (role){
                roleText = roleText + role.authority + ' ';
            })
            tr.append($('<td>').attr('id','roles' + index.toString()).text(roleText));
            tr.append($('<td>').append($('<button>').attr('type','button')
                .attr('class','btn btn-primary').attr('data-toggle','modal')
                .attr('data-target','#editModal').attr('data-whatever',index).html('Edit')));
            tr.append($('<td>').append($('<button>').attr('type','button')
                .attr('class','btn btn-danger').attr('data-toggle','modal')
                .attr('data-target','#deleteModal').attr('data-whatever',index).html('Delete')));
            body.prepend(tr.attr('id','row' + index.toString()));
        })
    }

    $('#deleteModal').on('shown.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let recipient = button.data('whatever');
        let modal = $(this);

        modal.find('#id').val(users[recipient].id);
        modal.find('#idModalDelete').val(users[recipient].id)
        modal.find('#nameModalDelete').val(users[recipient].name);
        modal.find('#lastNameModalDelete').val(users[recipient].lastName);
        modal.find('#ageModalDelete').val(users[recipient].age);
        modal.find('#usernameModalDelete').val(users[recipient].username);
        allRoles.forEach(function (role) {
            modal.find('#rolesModalDelete').append($('<option>')
                .attr('value',role.id).html(role.authority))
        })

        deleteForm.onsubmit = async (e) => {
            e.preventDefault();
            let response = await fetch('/admin-rest/'+ users[recipient].id.toString(), {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8',
                }
            });
            $("#row"+recipient.toString()).remove();
        }
    })
});
