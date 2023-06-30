let { createApp } = Vue;
createApp({
    data() {
        return {
            clientName: '',
            accounts: [],
            loans: [],
            isNavbarOpen: false
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get(`http://localhost:8080/api/clients/current`)
                .then(res => {
                    this.client = res.data
                    this.clientName = this.client.firstName + ' ' + this.client.lastName
                    this.accounts = this.client.accounts.sort((a, b) => a.id - b.id)
                    this.loans = this.client.loans.sort((a, b) => a.id - b.id)
                    console.log(this.client)
                }).catch(err => console.log(err))
        },
        sessionLogOut() {
            axios.post("/api/logout")
                .then(res => {
                    if (res.status == 200) {
                        Swal.fire({
                            position: 'top-center',
                            title: 'Bye bye!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        setTimeout(() => {
                            window.location.href = "/web/index.html";
                        }, 1800)
                    }
                    // window.location.href = "/web/index.html"
                    console.log(sres)
                }).catch(err => { console.log(err) })
        },
        createAccount() {
            axios.post("/api/clients/current/accounts")
            .then(res => {
                window.location.href = "/web/pages/accounts.html"
            }).catch(err => {console.log(err)})
        }
    }
}).mount("#app")

// axios.post('/api/logout').then(response => console.log('signed out!!!'))