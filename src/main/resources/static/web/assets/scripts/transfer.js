let { createApp } = Vue
createApp({
    data() {
        return {
            accounts: [],
            originAccountNumber: "",
            destinyAccountNumber: "",
            amount: null,
            description: '',
            resMsg: '',
            errMsg: '',
            selectedOption: '',
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        makeTransaction() {
            if (this.amount < 0) {
                Swal.fire({
                    position: 'center',
                    title: "Amount can't be negative",
                    showConfirmButton: false,
                    timer: 1500
                })
            } else {
                if (this.amount > 0 && this.description && this.originAccountNumber && this.destinyAccountNumber) {
                    Swal.fire({
                        title: 'Are you sure?',
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Yes, confirm transfer!'
                    }).then((res) => {
                        if (res.isConfirmed) {
                            console.log(this.amount)
                            axios.post("/api/transactions", `amount=${this.amount}&originAccountNumber=${this.originAccountNumber}&destinyAccountNumber=${this.destinyAccountNumber}&description=${this.description}`)
                                .then(res => {
                                    this.resMsg = res.data
                                    if (res.status == 200) {
                                        Swal.fire({
                                            position: 'center',
                                            title: `${this.resMsg}`,
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                        this.eraseFields()
                                    }
                                }).catch(err => {
                                    console.log(err)
                                    this.errMsg = err.response.data
                                    if (err.response.status == 403) {
                                        Swal.fire({
                                            position: 'center',
                                            title: `${this.errMsg}`,
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                        this.eraseFields()
                                    }
                                })
                        }
                    })
                } else {
                    Swal.fire({
                        position: 'center',
                        title: 'Please complete all data',
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            }

        },
        loadData() {
            console.log(this.originAccountNumber, this.destinyAccountNumber, this.amount)
            axios.get(`http://localhost:8080/api/clients/current`)
                .then(res => {
                    this.client = res.data
                    this.accounts = res.data.accounts.sort((a, b) => a.id - b.id)
                }).catch(err => {
                    console.log(err)
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
        },
        eraseFields() {
            this.originAccountNumber = '',
                this.destinyAccountNumber = '',
                this.amount = null,
                this.description = ''
        },
    }
}).mount("#app")