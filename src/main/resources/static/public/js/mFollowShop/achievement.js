function showAchievementModal(){
    const achievementModal = bootstrap.Modal.getOrCreateInstance("#achievementModal");
    loadMyAchievementList();
    achievementModal.show();
}
function closeAchievementModal(){
    const achievementModal = bootstrap.Modal.getOrCreateInstance("#achievementModal");
    achievementModal.hide();
}
function loadMyAchievementList(){
    const url = "/mFollow/loadMyAchievementList";
    fetch(url, {
        method: "post",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "user_pk="+user_pk
    })
    .then(response => response.json())
    .then((response) =>{
        const achievementListBox = document.querySelector("#achievementListBox");
        achievementListBox.innerHTML = "";
        for(e of response.data){
            const achievementListWrapper = document.querySelector("#achievementListTemplete .achievementListWrapper").cloneNode(true);
            const tier =  achievementListWrapper.querySelector(".tier");
            if(e.achievementResultDto.ach_level == 1){
                tier.classList.add("tier1");
                tier.src = "/public/image/mFollow/tier1.png";
            }else if(e.achievementResultDto.ach_level == 2){
                tier.classList.add("tier2");
                tier.src = "/public/image/mFollow/tier2.png";
            }else if(e.achievementResultDto.ach_level == 3){
                tier.classList.add("tier3");
                tier.src = "/public/image/mFollow/tier3.png";
            }else if(e.achievementResultDto.ach_level == 4){
                tier.classList.add("tier4");
                tier.src = "/public/image/mFollow/tier4.png";
            }else if(e.achievementResultDto.ach_level == 5){
                tier.classList.add("tier5");
                tier.src = "/public/image/mFollow/tier5.png";
            }

            const ach_name =  achievementListWrapper.querySelector(".ach_name");
            ach_name.innerText = e.achievementDto.achievement_name;
            
            const ach_level =  achievementListWrapper.querySelector(".ach_level");
            ach_level.innerText = "Lv."+e.achievementResultDto.ach_level;

            const ach_category =  achievementListWrapper.querySelector(".ach_category");
            ach_category.innerText = e.categoryName;
            
            const ach_aim =  achievementListWrapper.querySelector(".ach_aim");
            ach_aim.innerText = e.achievementDto.achievement_aim

            const ach_percent =  achievementListWrapper.querySelector(".ach_percent");
            
            const ach_count =  achievementListWrapper.querySelector(".ach_count");
            ach_count.innerText = e.achievementResultDto.my_count +" / "+e.achievementResultDto.ach_count;

            const ache_count = e.achievementResultDto.my_count - e.beforeCount;
            const my_count = e.achievementResultDto.ach_count - e.beforeCount;
            const percent = (ache_count/my_count)*100+"%";
            ach_percent.style.width = percent;

            if(e.achievementDto.achievement_pk ==8 || e.achievementDto.achievement_pk == 9){
                ach_count.style.left = "36%";
            }

            achievementListBox.appendChild(achievementListWrapper);
            
        }
    });
}