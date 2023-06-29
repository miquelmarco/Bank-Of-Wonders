let { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: '',
            registerFirstName: '',
            registerLastName: '',
            registerEmail: '',
            registerPassword: ''
        }
    },
    created() { },
    methods: {
        sessionLogIn() {
            if (this.email && this.password) {
                axios.post("/api/login", `email=${this.email}&password=${this.password}`,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(res => {
                        alert("Welcome!")
                        window.location.href = "/web/pages/accounts.html"
                    }).catch(err => { console.error(err) })
            } else {
                Swal.fire({
                    title: 'All fields are necessary!',
                    showClass: {
                        popup: 'animate__animated animate__fadeInDown'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__fadeOutUp'
                    }
                })
            }
        },
        register() {
            if (this.registerFirstName && this.registerLastName && this.registerEmail && this.registerPassword) {
                axios.post("/api/clients", `firstName=${this.registerFirstName}&lastName=${this.registerLastName}&email=${this.registerEmail}&password=${this.registerPassword}`,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(res => {
                        axios.post("/api/login", `email=${this.registerEmail}&password=${this.registerPassword}`,
                            { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                            .then(res => {
                                alert("welcome!")
                                window.location.href = "/web/pages/accounts.html"
                            }).catch(err => { console.error(err) })
                    }).catch(err => { console.error(err) })
            } else {
                Swal.fire({
                    title: 'All fields are necessary!',
                    showClass: {
                        popup: 'animate__animated animate__fadeInDown'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__fadeOutUp'
                    }
                })
            }
        }
    }
}).mount("#app")