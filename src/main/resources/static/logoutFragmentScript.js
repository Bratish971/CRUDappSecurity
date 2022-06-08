$(async function logoutFragment() {
    let responseCurrent = await fetch('/user-rest/current');
    let userCurrent = await responseCurrent.json();
    $('#currentUserName').html(userCurrent.username);
    let roleText = '';
    userCurrent.roles.forEach(function (role){
        roleText = roleText + role.authority + ' ';
    })
    $('#currentUserRoles').html(roleText)
});