setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                clients: [],
                // todoJSON: "",
                newClient: { firstName: '', lastName: '', email: '' }
            }
        },
        created() {
            this.loadData()
        },
        methods: {
            loadData() {
                axios.get(`http://localhost:8080/api/clients`)
                    .then(res => {
                        // this.todoJSON = res.data
                        this.clients = res.data
                        console.log(this.clients)
                    }).catch(err => console.error(err))
            },
            addClient() {
                if (this.newClient.firstName !== "" && this.newClient.lastName !== "" && this.newClient.email !== "") {
                    this.postClient();
                } else {
                    alert("All fields are necessary");
                }
            },
            postClient() {
                axios.post(`http://localhost:8080/rest/clients`, { firstName: this.newClient.firstName, lastName: this.newClient.lastName, email: this.newClient.email })
                    .then(res => {
                        this.loadData()
                        this.eraseFields()
                    }).catch(err => console.log(err))
            },
            sessionLogOut() {
                axios.post("/api/logout")
                    .then(res => {
                        if (res.status == 200) {
                            Swal.fire({
                                position: 'top-center',
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
        }
    }).mount("#app")
}, 1000)