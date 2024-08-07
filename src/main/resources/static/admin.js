$(document).ready(function() {
    let usersData = {};
    getUsers();
    getRoles();

    fetch('http://localhost:8080/api/user')
            .then(response => response.json())
            .then(user => {
                fillWithRoles(user);
    });

    async function getUsers() {
        fetch('http://localhost:8080/api/admin/users').then(response => response.json())
        .then(users => {
            usersData = users;
            fillTable();
        });
    }

    function getRoles() {
        return fetch('http://localhost:8080/api/admin/roles')
                .then(response => response.json())
                .then(roles => {
                    roles = sortAndModifyRoles(roles);
                    const rolesList = $('#rolesList');
                    rolesList.empty();
                    roles.forEach(role => {
                        const option = $('<option>').val(JSON.stringify(role)).text(role.name);
                        rolesList.append(option);
                    });
                    return roles;
                });
    }

    function saveUser() {
        const listVal = $('#rolesList').val();
        const rolesObj = listVal.map(val => JSON.parse(val));
        const user = {
            username: $('#username').val(),
            name:  $('#name').val(),
            lastname: $('#lastname').val(),
            age: $('#age').val(),
            password: $('#password').val(),
            roles: rolesObj
        };
        postData('http://localhost:8080/api/admin/users', user, 'POST')
            .then(data => {
                usersData.push(data);
                fillTable();
                $('#newUserForm').trigger('reset');
            });
    }

    function updateUser() {
        const listVal = $('#rolesListE').val();
        const rolesObj = listVal.map(val => JSON.parse(val));
        const user = {
            id: $('#idE').val(),
            username: $('#usernameE').val(),
            name:  $('#nameE').val(),
            lastname: $('#lastnameE').val(),
            age: $('#ageE').val(),
            roles: rolesObj
        };
        postData('http://localhost:8080/api/admin/users/' + user.id, user, 'PUT')
            .then(() => {
                let index = usersData.findIndex(u => u.id == user.id);
                usersData[index] = user;
                fillTable();
                $('#editUserModal').modal('hide');
            });
    }

    function deleteUser() {
        const userId = $('#idD').val();
        fetch('http://localhost:8080/api/admin/users/' + userId, {method: "DELETE"})
            .then(() => {
                usersData = usersData.filter(u => u.id != userId);
                fillTable();
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

    function fillTable() {
        let tableBody = $('#allUsersTable');
        tableBody.empty();
        usersData.forEach(us => {
        let roleNames = us.roles.map(role => role.name.replace('ROLE_', ''));
        let roleNamesStr = roleNames.join(" ");
        let rolesObj = JSON.stringify(us.roles);
        let row = `<tr id="${us.id}"><td><span>${us.id}</span></td>
                                  <td><span>${us.name}</span></td>
                                  <td><span>${us.lastname}</span></td>
                                  <td><span>${us.age}</span></td>
                                  <td><span>${us.username}</span></td>
                                  <td>
                                      <span class="roles">${roleNamesStr}</span>
                                  </td>
                                  <td>
                                    <button type="button" class="btn btn-info btn-edit" data-toggle="modal"
                                            data-target="#editUserModal" data-user-id="${us.id}"
                                            data-user-name="${us.name}" data-user-lastname="${us.lastname}"
                                            data-user-username="${us.username}" data-user-age="${us.age}"
                                            data-user-roles=${rolesObj}>
                                      Edit
                                    </button>
                                  </td>
                                  <td>
                                    <button type="button" class="btn btn-danger btn-delete" data-toggle="modal"
                                            data-target="#deleteUserModal" data-user-id="${us.id}"
                                            data-user-name="${us.name}" data-user-lastname="${us.lastname}"
                                            data-user-username="${us.username}" data-user-age="${us.age}"
                                            data-user-roles="${roleNames}">
                                      Delete
                                    </button>
                                  </td>
                                </tr>`;
            tableBody.append(row);
        });
    }

    $(document).on('click', '.btn.btn-info.btn-edit', function() {
        getRoles().then(allRoles => {
            var $btn = $(this);
            $('#idE').val($btn.attr('data-user-id'));
            $('#usernameE').val($btn.attr('data-user-username'));
            $('#nameE').val($btn.attr('data-user-name'));
            $('#lastnameE').val($btn.attr('data-user-lastname'));
            $('#ageE').val($btn.attr('data-user-age'));
            const userRoles = JSON.parse($btn.attr('data-user-roles'));
            const rolesList = $('#rolesListE');
            rolesList.empty();
            allRoles.forEach(role => {
                const option = $('<option>').val(JSON.stringify(role)).text(role.name);
                if (userRoles.find(r => role.id == r.id)) {
                    option.attr('selected', true);
                }
                rolesList.append(option);
            });
        });
    });

    $(document).on('click', '.btn.btn-danger.btn-delete', function() {
        let $btn = $(this);
        $('#idD').val($btn.attr('data-user-id'));
        $('#usernameD').val($btn.attr('data-user-username'));
        $('#nameD').val($btn.attr('data-user-name'));
        $('#lastnameD').val($btn.attr('data-user-lastname'));
        $('#ageD').val($btn.attr('data-user-age'));
        let roles = $btn.attr('data-user-roles').split(',');
        const rolesList = $('#rolesListD');
        rolesList.empty();
        roles.forEach(role => {
            const option = $('<option>').text(role);
            rolesList.append(option);
        });
    });

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
});