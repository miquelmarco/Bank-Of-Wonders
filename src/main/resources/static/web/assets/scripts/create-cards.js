let { createApp } = Vue;
createApp({
    data() {
        return {
            cardType: '',
            cardColor: '',
            errMsg: '',
            cardCreatedMsg: '',
        }
    },
    created() {
    },
    methods: {
        createCard() {
            axios.post("/api/clients/current/cards", `cardType=${this.cardType}&cardColor=${this.cardColor}`)
                .then(res => {
                    this.cardCreatedMsg = res.data
                    if (res.status == 200) {
                        Swal.fire({
                            position: 'center',
                            title: `${this.cardCreatedMsg}`,
                            showConfirmButton: false,
                            timer: 1500
                        })
                        window.location.href = "/web/pages/cards.html"
                    }
                }).catch(err => {
                    this.errMsg = err.response.data
                    Swal.fire({
                        title: `${this.errMsg}`,
                        showClass: {
                            popup: 'animate__animated animate__fadeInDown'
                        },
                        hideClass: {
                            popup: 'animate__animated animate__fadeOutUp'
                        }
                    })
                })
        },
        sessionLogOut() {
            axios.post("/api/logout")
                .then(res => {
                    if (res.status == 200) {
                        Swal.fire({
                            position: 'center',
                            title: 'Bye bye!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        setTimeout(() => {
                            window.location.href = "/web/index.html";
                        }, 1800)
                    }
                    console.log(res)
                }).catch(err => { console.log(err) })
        }
    }
}).mount("#app")