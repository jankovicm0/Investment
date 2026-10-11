(function () {
    'use strict';

    async function post(url, payload, responseType) {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const body = responseType === 'json'
            ? await response.json().catch(() => null)
            : await response.text();
        if (!response.ok) {
            throw new Error(typeof body === 'string' && body
                ? body
                : 'Request failed. Please check your details.');
        }
        return body;
    }

    function bindForm(formId, statusId, url, responseType, getPayload, successText) {
        const form = document.getElementById(formId);
        const status = document.getElementById(statusId);
        form.addEventListener('submit', async function (event) {
            event.preventDefault();
            const button = form.querySelector('button[type="submit"]');
            button.disabled = true;
            status.classList.remove('error');
            status.textContent = 'Sending request…';
            try {
                const body = await post(url, getPayload(), responseType);
                if (responseType === 'json' && (!body || !body.jwt)) {
                    throw new Error('The server did not return an authentication token.');
                }
                status.textContent = successText;
                form.querySelector('input[type="password"]').value = '';
            } catch (error) {
                status.classList.add('error');
                status.textContent = error.message || 'Unable to reach the API.';
            } finally {
                button.disabled = false;
            }
        });
    }

    bindForm('register-form', 'register-status', '/api/users/register', 'text', function () {
        return {
            username: document.getElementById('register-username').value.trim(),
            password: document.getElementById('register-password').value
        };
    }, 'Demo account created. You can now try signing in.');

    bindForm('login-form', 'login-status', '/api/users/login', 'json', function () {
        return {
            username: document.getElementById('login-username').value.trim(),
            password: document.getElementById('login-password').value
        };
    }, 'Login succeeded; a demo JWT was issued (not stored on this page).');
})();
