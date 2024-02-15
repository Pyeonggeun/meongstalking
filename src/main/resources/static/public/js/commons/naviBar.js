let user_pk_Navi = null;
function getNaviUserDto(){
    const url = "/mstar/getUserDto"
    fetch(url)
    .then(response => response.json())
    .then((response) =>{
        user_pk_Navi = response.data.user_pk;
        checkUnReadNotify(user_pk_Navi);
        checkUnReadDirect(user_pk_Navi);
    });
}

function showNotifyModal(){
    const notifyModal = bootstrap.Modal.getOrCreateInstance("#notifyModal");
    loadUnReadMyNotificationList();
    loadReadMyNotificationList();
    notifyModal.show();
}
function closeNotifyModal(){
    const notifyModal = bootstrap.Modal.getOrCreateInstance("#notifyModal");
    notifyModal.hide();
}

function loadUnReadMyNotificationList(){
    const url = "/mstar/loadUnReadMyNotificationList";
    
    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "user_pk="+user_pk_Navi
    })
    .then(response => response.json())
    .then((response) =>{
        const unReadNotifyListBox = document.querySelector("#unReadNotifyListBox");
        unReadNotifyListBox.innerHTML = "";
        if(response.data.length != 0){
            for(e of response.data){
            
                const unReadNotifyListWrapper = document.querySelector("#unReadNotifyListTemplete .unReadNotifyListWrapper").cloneNode(true);
                
                const unReadImg = unReadNotifyListWrapper.querySelector(".unReadImg");
                const unReadIcon = unReadNotifyListWrapper.querySelector(".unReadIcon");
                if(e.notify_img != "N"){
                    unReadImg.src = e.notify_img;
                }else {
                    unReadImg.remove();
                    unReadIcon.classList.add("rounded")
                    unReadIcon.setAttribute("style", "border: 0.2em solid gray;")
                    const i = document.createElement("i");
                    i.classList.add("fa-solid", "fa-bullhorn");
                    unReadIcon.appendChild(i);
                }
                const unReadTitle = unReadNotifyListWrapper.querySelector(".unReadTitle");
                unReadTitle.innerText = e.title;
                const unReadMessage = unReadNotifyListWrapper.querySelector(".unReadMessage");
                unReadMessage.innerText = e.message;
                const unReadCreated = unReadNotifyListWrapper.querySelector(".unReadCreated");
                unReadCreated.innerText = e.created_at[0]+"."+e.created_at[1]+"."+e.created_at[2]+"."; 

                unReadNotifyListBox.appendChild(unReadNotifyListWrapper);
            }
        }else{
            unReadNotifyListBox.classList.add("text-center", "text-secondary");
            unReadNotifyListBox.innerText = "새로운 알림이 없습니다.";
        }
        const unReadNotify = document.querySelector("#unReadNotify");
        unReadNotify.classList.add("d-none");
        updateNotifyReadStatus(user_pk_Navi);
    });
}
function loadReadMyNotificationList(){
    const url = "/mstar/loadReadMyNotificationList";
    
    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "user_pk="+user_pk_Navi
    })
    .then(response => response.json())
    .then((response) =>{
        const readNotifyListBox = document.querySelector("#readNotifyListBox");
        readNotifyListBox.innerHTML = "";
        
        if(response.data.length != 0){
            for(e of response.data){
                const readNotifyListWrapper = document.querySelector("#readNotifyListTemplete .readNotifyListWrapper").cloneNode(true);
                
                const readImg = readNotifyListWrapper.querySelector(".readImg");
                const readIcon = readNotifyListWrapper.querySelector(".readIcon");
                if(e.notify_img != "N"){
                    readImg.src = e.notify_img;
                }else {
                    readImg.remove();
                    const i = document.createElement("i");
                    i.classList.add("fa-solid", "fa-bullhorn");
                    readIcon.appendChild(i);
                }
                const readTitle = readNotifyListWrapper.querySelector(".readTitle");
                readTitle.innerText = e.title;
                const readMessage = readNotifyListWrapper.querySelector(".readMessage");
                readMessage.innerText = e.message;
                const readCreated = readNotifyListWrapper.querySelector(".readCreated");
                readCreated.innerText = e.created_at[0]+"."+e.created_at[1]+"."+e.created_at[2]+"."; 

                readNotifyListBox.appendChild(readNotifyListWrapper);
            }
        }else{
            readNotifyListBox.classList.add("text-center", "text-secondary");
            readNotifyListBox.innerText = "이전 알림이 없습니다.";
        }
        
    });
}
function checkUnReadNotify(){
    const url = "/mstar/checkUnReadNotify";
    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "user_pk="+user_pk_Navi
    })
    .then(response => response.json())
    .then((response) =>{
        if(response.data !=0){
            const unReadNotify = document.querySelector("#unReadNotify");
            unReadNotify.classList.remove("d-none");
        }
        
    });
}
function checkUnReadDirect(){
    const url = "/mstar/checkUnReadDirect";
    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "user_pk="+user_pk_Navi
    })
    .then(response => response.json())
    .then((response) =>{
        if(response.data !=0){
            const unReadDirect = document.querySelector("#unReadDirect");
            unReadDirect.classList.remove("d-none");
        }
        
    });
}

function updateNotifyReadStatus(){
    const url = "/mstar/updateNotifyReadStatus";
    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "user_pk="+user_pk_Navi
    });

}
window.addEventListener("DOMContentLoaded",()=>{
    getNaviUserDto();
})
