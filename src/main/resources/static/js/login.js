document.querySelector('.toggle-password-btn').addEventListener('click', function () {
    const passwordBox = document.querySelector(this.getAttribute('toggle'));
    const eyeIcon = document.querySelector('.eye');
    if (passwordBox.getAttribute("type") === "password") {
        passwordBox.setAttribute("type", "text");
        eyeIcon.innerHTML = 'visibility_off';
    } else {
        passwordBox.setAttribute("type", "password");
        eyeIcon.innerHTML = 'visibility';
    }
});
