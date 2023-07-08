let { createApp } = Vue;
createApp({
    data() {
        return {
            clientName: '',
            accounts: [],
            mortgageLoan: {},
            personalLoan: {},
            automotiveLoan: {},
            destAcc: '',
            paym: 0,
            amou: null,
            loanType: 0,
            loanTypeData: [],
            mortgagePayments: [],
            personalPayments: [],
            automotivePayments: [],
            errMsg: ''
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
                    this.mortgageLoan = this.loanTypeData[0]
                    this.personalLoan = this.loanTypeData[1]
                    this.automotiveLoan = this.loanTypeData[2]
                    this.mortgagePayments = this.loanTypeData[0].payments
                    this.personalPayments = this.loanTypeData[1].payments
                    this.automotivePayments = this.loanTypeData[2].payments
                })
        },
        getClientAccounts() {
            axios.get(`/api/clients/current`)
                .then(res => {
                    console.log(res)
                    this.client = res.data
                    this.clientName = this.client.firstName + ' ' + this.client.lastName
                    this.accounts = this.client.accounts.sort((a, b) => a.id - b.id)
                }).catch(err => console.log(err))
        },
        loanRequest() {
            if (this.amou < 10000) {
                Swal.fire({
                    position: 'center',
                    title: 'the minimum amount to request is $10.000',
                    showConfirmButton: false,
                    timer: 1500
                })
            } else {
                if (this.amou >= 10000 && this.loanType && this.paym && this.destAcc) {
                    console.log(this.amou, this.loanType, this.paym, this.destAcc)
                    axios.post("/api/loans",
                        { loanTypeId: `${this.loanType}`, amount: `${this.amou}`, payments: `${this.paym}`, destinationAcc: `${this.destAcc}` })
                        .then(res => {
                            Swal.fire({
                                position: 'center',
                                title: 'LOAN APPROVED',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            this.eraseFields()
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
                } else {
                    Swal.fire({
                        position: 'center',
                        title: 'please complete all the data',
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
    }
}).mount("#app")