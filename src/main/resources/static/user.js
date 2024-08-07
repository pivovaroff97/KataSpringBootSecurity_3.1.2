$(document).ready(function() {
    fetch('http://localhost:8080/api/user')
        .then(response => response.json())
        .then(user => {
            fillWithRoles(user);
            let roleNames = roles.map(r => r.name).join(" ");
            let tableBody = $('#userTable');
            tableBody.empty();
            let row = `<tr id="${user.id}"><td><span>${user.id}</span></td>
                                     <td><span>${user.name}</span></td>
                                     <td><span>${user.lastname}</span></td>
                                     <td><span>${user.age}</span></td>
                                     <td><span>${user.username}</span></td>
                                     <td>
                                         <span class="roles">${roleNames}</span>
                                     </td>
                                   </tr>`;
            tableBody.append(row);
        });
});