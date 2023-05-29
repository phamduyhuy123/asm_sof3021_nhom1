const toastTrigger = document.querySelector('#liveToastBtn')
const toastLiveExample = document.querySelector('#liveToast')

console.log(toastTrigger);
if (toastTrigger) {

    toastTrigger.addEventListener('click', () => {
        const toast = new bootstrap.Toast(toastLiveExample)

        toast.show()
    })
}