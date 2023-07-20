setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                accounts: [],
                loanTypeData: [],
                destAcc: '',
                paym: 0,
                amou: null,
                loanType: null,
                errMsg: '',
            }
        },
        created() {
            this.getLoanData()
            this.getClientAccounts()
        },
        methods: {
            getLoanData() {
                axios.get("/api/loans")
                    .then(res => {
                        this.loanTypeData = res.data
                        console.log(this.loanTypeData, this.loanType)
                    })
            },
            getClientAccounts() {
                axios.get(`/api/clients/current/accounts`)
                    .then(res => {
                        this.accounts = res.data.sort((a, b) => a.id - b.id)
                    }).catch(err => console.log(err))
            },
            loanRequest() {
                if (this.amou < 10000) {
                    Swal.fire({
                        position: 'center',
                        icon: 'warning',
                        title: 'the minimum amount to request is $10.000',
                        showConfirmButton: false,
                        timer: 1500
                    })
                } else {
                    if (this.amou >= 10000 && this.loanType && this.paym && this.destAcc) {
                        Swal.fire({
                            title: 'Do you want apply this loan?',
                            showDenyButton: true,
                            showCancelButton: true,
                            icon: 'question',
                            confirmButtonText: 'Confirm request',
                            denyButtonText: `Go back`,
                        }).then((res) => {
                            if (res.isConfirmed) {
                                axios.post("/api/loans",
                                    { loanTypeId: `${this.loanType}`, amount: `${this.amou}`, payments: `${this.paym}`, destinationAcc: `${this.destAcc}` })
                                    .then(res => {
                                        Swal.fire({
                                            position: 'center',
                                            icon: 'success',
                                            title: 'LOAN APPROVED',
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                        this.eraseFields()
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
                            } else {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'warning',
                                    title: 'Loan not requested',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                this.eraseFields()
                            }
                        }
                        )
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            position: 'center',
                            title: 'Please complete all the data',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                }
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
                    }).catch(err => { console.log(err) })
            },
            eraseFields() {
                this.amou = null,
                    this.loanType = 0,
                    this.destAcc = '',
                    this.paym = 0
            },
        },
        computed: {
            selectedLoanType() {
                return this.loanTypeData.find(loanType => loanType.id === this.loanType);
            },
            selectedLoanTypePayments() {
                return this.selectedLoanType ? this.selectedLoanType.payments : [];
            },
        },
    }).mount("#app")
}, 1000)