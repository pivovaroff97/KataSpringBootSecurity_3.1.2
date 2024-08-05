$(document).ready(function() {
    getUsers();

    async function getUsers() {
        let roles = await getRoles();
        fetch('http://localhost:8080/admin/users').then(response => response.json())
        .then(users => {
            const rolesList = $('#rolesList');
            rolesList.empty();
            roles.forEach(role => {
                const option = $('<option>').val(role.id).text(role.name);
                rolesList.append(option);
            });
            let tableBody = $('#userTableBody');
            tableBody.empty();
            users.forEach(user => {
                let row = $('<tr>');
                row.append($('<td>').append($('<span>').text(user.id)));
                row.append($('<td>').append($('<span>').text(user.name)));
                row.append($('<td>').append($('<span>').text(user.lastname)));
                row.append($('<td>').append($('<span>').text(user.age)));
                row.append($('<td>').append($('<span>').text(user.username)));
                row.append($('<td>').append($('<span>').text(
                                                        user.roles.map(role => role.name)
                                                        .join(" ")
                                                        .replaceAll("ROLE_", ""))));
                row.append($('<td>').append($('<button>').addClass('btn btn-info btn-edit')
                                                   .text('Edit')
                                                   .attr('data-toggle', 'modal')
                                                   .attr('data-target', '#editUserModal')
                                                   .attr('data-user-id', user.id)
                                                   .attr('data-user-username', user.username)
                                                   .attr('data-user-name', user.name)
                                                   .attr('data-user-lastname', user.lastname)
                                                   .attr('data-user-age', user.age)
                                                   .attr('data-user-roles', user.roles.map(role => role.id))));
                row.append($('<td>').append($('<button>').addClass('btn btn-danger btn-delete')
                                                   .text('Delete')
                                                   .attr('data-toggle', 'modal')
                                                   .attr('data-target', '#deleteUserModal')
                                                   .attr('data-user-id', user.id)
                                                   .attr('data-user-username', user.username)
                                                   .attr('data-user-name', user.name)
                                                   .attr('data-user-lastname', user.lastname)
                                                   .attr('data-user-age', user.age)
                                                   .attr('data-user-roles',
                                                                    JSON.stringify(
                                                                            user.roles
                                                                            .map(role =>
                                                                                role.name.replace('ROLE_', ''))))));
                tableBody.append(row);
            });
        });
    }

    function getRoles() {
        return fetch('http://localhost:8080/admin/roles')
                .then(response => response.json())
                .then(roles => {
                    return roles.map(role => {
                        return {
                            id: role.id,
                            name: role.name.replace('ROLE_', '')
                        }
                    });
                });
    }

    function saveUser() {
        const user = {
            username: $('#username').val(),
            name:  $('#name').val(),
            lastname: $('#lastname').val(),
            age: $('#age').val(),
            password: $('#password').val(),
            roleIds: $('#rolesList').val()
        };
        postData('http://localhost:8080/admin/users', user, 'POST')
            .then(() => {
                getUsers();
                $('#newUserForm').trigger('reset');
            });
    }

    function updateUser() {
        const user = {
            id: $('#idE').val(),
            username: $('#usernameE').val(),
            name:  $('#nameE').val(),
            lastname: $('#lastnameE').val(),
            age: $('#ageE').val(),
            roleIds: $('#rolesListE').val()
        };
        postData('http://localhost:8080/admin/users/' + user.id, user, 'PUT')
            .then(() => {
                getUsers();
                $('#editUserModal').modal('hide');
            });
    }

    function deleteUser() {
        const userId = $('#idD').val();
        fetch('http://localhost:8080/admin/users/' + userId, {method: "DELETE"})
            .then(() => {
                getUsers();
                $('#deleteUserModal').modal('hide');
            });
    }

    async function postData(url, data, methodName) {
      const response = await fetch(url, {
        method: methodName,
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      });
      return await response.json();
    }

    $("#newUserForm").submit(function(event) {
        event.preventDefault();
        saveUser();
    });

    $("#editFormAction").click(function(event) {
        event.preventDefault();
        updateUser();
    });

    $("#deleteFormAction").click(function(event) {
        event.preventDefault();
        deleteUser();
    });

    $(document).on('click', '.btn.btn-info.btn-edit', function() {
        getRoles().then(allRoles => {
            var $btn = $(this);
            $('#idE').val($btn.attr('data-user-id'));
            $('#usernameE').val($btn.attr('data-user-username'));
            $('#nameE').val($btn.attr('data-user-name'));
            $('#lastnameE').val($btn.attr('data-user-lastname'));
            $('#ageE').val($btn.attr('data-user-age'));
            const rolesList = $('#rolesListE');
            rolesList.empty();
            allRoles.forEach(role => {
                const option = $('<option>').val(role.id).text(role.name).attr('id', role.id);
                rolesList.append(option);
            });
            const userRoles = $btn.attr('data-user-roles');
            for (const r of allRoles) {
                if (userRoles.includes(r.id)) {
                    $('#' + r.id).attr('selected', true);
                }
            }
        });
    });

    $(document).on('click', '.btn.btn-danger.btn-delete', function() {
        let $btn = $(this);
        $('#idD').val($btn.attr('data-user-id'));
        $('#usernameD').val($btn.attr('data-user-username'));
        $('#nameD').val($btn.attr('data-user-name'));
        $('#lastnameD').val($btn.attr('data-user-lastname'));
        $('#ageD').val($btn.attr('data-user-age'));
        let roles = JSON.parse($btn.attr('data-user-roles'));
        const rolesList = $('#rolesListD');
        rolesList.empty();
        roles.forEach(role => {
            const option = $('<option>').text(role);
            rolesList.append(option);
        });
    });
});