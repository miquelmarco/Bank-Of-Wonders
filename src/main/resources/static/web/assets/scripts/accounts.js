setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                clientName: '',
                accounts: [],
                loans: [],
                selectedAccountToDelete: '',
                accountType: '',
                errMsg: '',
                errMsg2: ''
            }
        },
        created() {
            this.getLoans()
            this.getActiveAccounts()
        },
        methods: {
            getActiveAccounts() {
                axios.get(`/api/clients/current/accounts`)
                    .then(res => {
                        this.accounts = res.data.sort((a, b) => a.id - b.id)
                    }).catch(err => console.log(err))
            },
            getLoans() {
                axios.get(`/api/clients/current/loans`)
                    .then(res => {
                        this.loans = res.data.sort((a, b) => a.id - b.id)
                    }).catch(err => console.log(err))
            },
            createAccount() {
                Swal.fire({
                    title: `You want to create a new ${this.accountType} account?`,
                    showDenyButton: true,
                    showCancelButton: true,
                    confirmButtonText: 'Confirm Account',
                    denyButtonText: `Go back`,
                }).then((res) => {
                    if (res.isConfirmed) {
                        axios.post(`/api/clients/current/accounts?type=${this.accountType}`)
                            .then(res => {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Account created successfully!',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                setTimeout(() => {
                                    window.location.href = "/web/pages/accounts.html"
                                }, 1800)
                            }).catch(err => {
                                this.errMsg = err.response.data
                                Swal.fire({
                                    position: 'center',
                                    icon: 'error',
                                    title: `${this.errMsg}`,
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                            })
                    }
                })
            },
            deleteAccount(accountNumber) {
                Swal.fire({
                    title: 'Do you want to delete the card?',
                    showDenyButton: true,
                    showCancelButton: true,
                    confirmButtonText: 'Delete Account',
                    denyButtonText: `Go back`,
                }).then((res) => {
                    if (res.isConfirmed) {
                        this.selectedAccountToDelete = accountNumber
                        axios.delete(`/api/clients/current/accounts?number=${this.selectedAccountToDelete}`)
                            .then(res => {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Account Deleted',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                setTimeout(() => {
                                    window.location.reload();
                                }, 1800)
                            }).catch(err => {
                                this.errMsg2 = err.response.data
                                Swal.fire({
                                    position: 'center',
                                    icon: 'error',
                                    title: `${this.errMsg2}`,
                                    showConfirmButton: false,
                                    timer: 2000
                                })
                            })
                    }
                })
            },
            paymentsCalculator(amount, payments) {
                let division = amount / payments
                let formatDivision = parseFloat(division.toFixed(2))
                return formatDivision.toLocaleString()
            },
            sessionLogOut() {
                axios.post("/api/logout")
                    .then(res => {
                        if (res.status == 200) {
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
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
        },
    },
    ).mount("#app")
}, 1000)