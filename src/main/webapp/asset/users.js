var editUserModal = document.getElementById("editUserModal");
editUserModal.addEventListener("show.bs.modal", function (event) {

    var button = event.relatedTarget;

    var userId = button.getAttribute("data-bs-userId");
    var userName = button.getAttribute("data-bs-userName");
    var code = button.getAttribute("data-bs-code");
    var birthday = button.getAttribute("data-bs-birthday");
    var initTime = button.getAttribute("data-bs-initTime");

    var modalBodyInputUserId = editUserModal.querySelector("#userId");
    var modalBodyInputUserName = editUserModal.querySelector("#userName");
    var modalBodyInputCode = editUserModal.querySelector("#code");
    var modalBodyInputBirthday = editUserModal.querySelector("#birthday");
    var modalBodyInputInitTime = editUserModal.querySelector("#initTime");

    modalBodyInputUserId.value = userId;
    modalBodyInputUserName.value = userName;
    modalBodyInputCode.value = code;
    modalBodyInputBirthday.value = birthday;
    modalBodyInputInitTime.value = initTime;
});

var delUserModal = document.getElementById("delUserModal");
delUserModal.addEventListener("show.bs.modal", function (event) {

    var button = event.relatedTarget;

    var userId = button.getAttribute("data-bs-userId");

    var modalBodyInputUserId = delUserModal.querySelector("#delUserId");

    modalBodyInputUserId.value = userId;
});

