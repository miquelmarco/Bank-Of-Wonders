let { createApp } = Vue
createApp({
    data() {
        return {
            email: '',
            password: '',
            registerFirstName: '',
            registerLastName: '',
            registerEmail: '',
            registerPassword: '',
        }
    },
    created() { },
    methods: {
        sessionLogIn() {
            if (this.email && this.password) {
                if (this.email == "admin@homebanking.com") {
                    axios.post("/api/login", `email=${this.email}&password=${this.password}`,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(res => {
                            if (res.status == 200) {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Welcome!',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                setTimeout(() => {
                                    window.location.href = "/web/manager.html";
                                }, 1800)
                            }
                        }).catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
                } else {
                    axios.post("/api/login", `email=${this.email}&password=${this.password}`,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(res => {
                            if (res.status == 200) {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Welcome!',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                setTimeout(() => {
                                    window.location.href = "/web/pages/accounts.html";
                                }, 1800)
                            }
                        }).catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
                }
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
            axios.post("/api/clients", `firstName=${this.registerFirstName}&lastName=${this.registerLastName}&email=${this.registerEmail}&password=${this.registerPassword}`,
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(res => {
                    axios.post("/api/login", `email=${this.registerEmail}&password=${this.registerPassword}`,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(res => {
                            if (res.status == 200) {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Resgistered, Welcome!',
                                    showConfirmButton: false,
                                    timer: 2000
                                })
                                setTimeout(() => {
                                    window.location.href = "/web/pages/accounts.html";
                                }, 2100)
                            }
                        }).catch(err => { console.error(err) })
                }).catch(err => {
                    this.errMsg = err.response.data
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: `${this.errMsg}`,
                        showConfirmButton: false,
                        timer: 2000
                    })
                })
        }
    }
}).mount("#app")