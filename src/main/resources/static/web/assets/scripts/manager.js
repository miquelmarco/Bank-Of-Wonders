setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                clients: [],
                newClient: { firstName: '', lastName: '', email: '', password: '' },
                loanName: '',
                loanMaxAmount: null,
                loanPayments: [],
                loanPercentage: null,
                loanDTO: {
                    name: '',
                    payments: [],
                    maxAmount: null,
                    percentage: null
                },
                inputNumber: null
            }
        },
        created() {
            this.loadData()
        },
        methods: {
            loadData() {
                axios.get(`http://localhost:8080/api/clients`)
                    .then(res => {
                        this.clients = res.data
                    }).catch(err => console.error(err))
            },
            addClient() {
                if (this.newClient.firstName !== "" && this.newClient.lastName !== "" && this.newClient.email !== "" && this, newClient.password !== "") {
                    this.postClient();
                } else {
                    alert("All fields are necessary");
                }
            },
            postClient() {
                axios.post(`http://localhost:8080/rest/clients`, { firstName: this.newClient.firstName, lastName: this.newClient.lastName, email: this.newClient.email, password: this.newClient.password })
                    .then(res => {
                        this.loadData()
                        this.eraseFields()
                    }).catch(err => console.log(err))
            },
            sendLoan() {
                axios.post("/api/loans/new",
                    {
                        name: `${this.loanName}`,
                        maxPayment: `${this.loanPayments}`,
                        maxAmount: `${this.loanMaxAmount}`,
                        percentage: `${this.loanPercentage}`
                    })
                    .then(res => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Loan Created',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        this.eraseFields()
                    }).catch(err => {
                        this.error = err.response.data
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: `${this.error} Try again!`,
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
            },
            sendLoan2() {
                axios.post("/api/loans/new", this.loanDTO)
                    .then(res => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Loan Created',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        this.clearForm()
                    }).catch(err => {
                        this.error = err.response.data
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: `${this.error} Try again!`,
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
            },
            addNumber() {
                if (this.inputNumber !== null) {
                    this.loanDTO.payments.push(this.inputNumber)
                    this.inputNumber = null
                }
            },
            clearPayments() {
                this.loanDTO.payments = []
            },
            clearForm() {
                this.loanDTO.name = ''
                this.loanDTO.payments = []
                this.loanDTO.maxAmount = null
                this.loanDTO.percentage = null
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
            eraseFields() {
                this.newClient.firstName = '',
                    this.newClient.lastName = '',
                    this.newClient.email = ''
            },
            deleteClient(id) {
                axios.delete(id)
                    .then(res => {
                        this.loadData()
                    }).catch(err => console.log(err))
            },
        },
    }).mount("#app")
}, 1000)