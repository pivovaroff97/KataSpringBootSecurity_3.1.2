function fillWithRoles(user) {
    roles = sortAndModifyRoles(user.roles);
    let navSideBar = $('#v-pills-tab');
    navSideBar.empty();
    if (roles.find(r => r.name == 'ADMIN')) {
        navSideBar.append(`<a class="${window.location.href.endsWith('admin') ? 'nav-link active' : 'nav-link'}"
                                role="tab" href="/admin">admin</a>`);
        navSideBar.append(`<a class="${window.location.href.endsWith('user') ? 'nav-link active' : 'nav-link'}"
                                role="tab" href="/user">user</a>`);
    } else {
        navSideBar.append(`<a class="nav-link active" role="tab" href="/user">user</a>`);
    }
    let roleNamesStr = roles.map(r => r.name).join(" ");
    let header = $('#headerInfo');
    header.append(`<span>${user.username} with roles: ${roleNamesStr}</span>`);
}

function sortAndModifyRoles(roles) {
    roles.sort((a, b) => a.name > b.name ? 1 : -1);
    return roles.map(r => {
        return {
            id: r.id,
            name: r.name.replace('ROLE_', '')
        }
    });
}