document.querySelector('form').addEventListener('submit', event => {
    if (typeof grecaptcha !== 'undefined') {
        const resp = grecaptcha.getResponse();
        if (resp.length === 0) {
            const captchaErrorElement = document.querySelector("#captchaError");
            captchaErrorElement.style.display = '';
            captchaErrorElement.innerHTML = 'Please verify that you are not a robot.';
            event.preventDefault();
        }
    }
})

window.onReCaptchaSuccess = () => {
    const captchaErrorElement = document.querySelector("#captchaError");
    captchaErrorElement.style.display = 'none';
    captchaErrorElement.innerHTML = "";
};

window.onReCaptchaExpired = () => {
    const captchaErrorElement = document.querySelector("#captchaError");
    captchaErrorElement.style.display = '';
    captchaErrorElement.innerHTML = "reCaptcha has expired. Please solve a new reCaptcha";
    grecaptcha.reset();
};
