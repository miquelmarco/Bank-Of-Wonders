setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                activeDebitCards: [],
                activeCreditCards: [],
                selectedCardToDelete: ''
            }
        },
        created() {
            this.getCards()
        },
        methods: {
            getCards() {
                axios.get('/api/clients/current/cards')
                    .then(res => {
                        this.clientCards = res.data
                        this.activeDebitCards = this.clientCards.filter(card => card.type == "DEBIT").sort((a, b) => a.id - b.id)
                        this.activeCreditCards = this.clientCards.filter(card => card.type == "CREDIT").sort((a, b) => a.id - b.id)
                    }).catch(err => {
                        Swal.fire({
                            position: 'center',
                            title: 'Cant load data, please refresh!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
            },
            deleteCard(cardNumber) {
                Swal.fire({
                    title: 'Do you want to delete the card?',
                    showDenyButton: true,
                    showCancelButton: true,
                    confirmButtonText: 'Delete',
                    denyButtonText: `Go back`,
                }).then((result) => {
                    if (result.isConfirmed) {
                        this.selectedCardToDelete = cardNumber
                        axios.delete(`/api/clients/current/cards?cardNumber=${this.selectedCardToDelete}`)
                            .then(res => {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Card deleted',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                                setTimeout(() => {
                                    window.location.reload();
                                }, 1800)
                            }).catch(err => {
                                console.log(err)
                                Swal.fire({
                                    position: 'center',
                                    title: 'Card cant be deleted, try again!',
                                    showConfirmButton: false,
                                    timer: 1500
                                })
                            })
                    } else if (result.isDenied) {
                        Swal.fire('Card not deleted', '', 'info')
                    }
                })
            },
            changeCardColor(card) {
                let currentDate = new Date()
                if (card.color === 'SILVER') {
                    if (currentDate < new Date(card.thruDate)) {
                        return 'silverBg'
                    } else return 'expiredCard'
                } else if (card.color === 'GOLD') {
                    if (currentDate < new Date(card.thruDate)) {
                        return 'goldBg'
                    } else return 'expiredCard'
                } else if (card.color === 'TITANIUM') {
                    if (currentDate < new Date(card.thruDate)) {
                        return 'titaniumBg'
                    } else return 'expiredCard'
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
                        console.log(res)
                    }).catch(err => {
                        Swal.fire({
                            position: 'center',
                            title: 'Cant log out, try again!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
            }
        }
    }).mount("#app")
}, 1000)