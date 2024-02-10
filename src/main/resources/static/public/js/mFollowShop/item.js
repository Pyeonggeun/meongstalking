    function getUrlKey() {
        const urlParams = new URLSearchParams(location.search);
        if(urlParams.get("resultPay")!=null){
            showShopModal();
            getMyCoin();
        }
    }


    function showShopModal(){
        const itemModal = bootstrap.Modal.getOrCreateInstance("#itemModal");
        loadShopItemList();
        getMyCoin();
        itemModal.show();
    }
    function closeShopModal(){
        const itemModal = bootstrap.Modal.getOrCreateInstance("#itemModal");
        itemModal.hide();
    }
    function getMyCoin(){
        const url = "/mFollow/getMyCoin";
        fetch(url, {
            method: "post",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "user_pk="+user_pk
        })
        .then(response => response.json())
        .then((response) =>{
            const myCoin = document.querySelector("#myCoin")
            myCoin.innerText = response.data;
        });
    }

    function loadCoinProductList(){
        const url = "/mFollow/loadCoinProductList";

        fetch(url)
        .then(response => response.json())
        .then((response) =>{
            const coinProductListBox = document.querySelector("#coinProductListBox");

            for(e of response.data){
                const coinProductListWrapper = document.querySelector("#coinProductListTemplete .coinProductListWrapper").cloneNode(true);
                coinProductListWrapper.setAttribute("onclick" , "loadCoinInfo("+e.coinCategoryDto.coin_category_pk+")");

                const coinProductImg = coinProductListWrapper.querySelector(".coinProductImg");
                coinProductImg.src = e.coinCategoryDto.coin_img

                const coinProductCount = coinProductListWrapper.querySelector(".coinProductCount");
                coinProductCount.innerText = "고기 보석"+e.coinCategoryDto.coin_count+"개 + "+e.coinCategoryDto.bonus_coin+"개";

                const coinProductPrice = coinProductListWrapper.querySelector(".coinProductPrice");
                coinProductPrice.innerText = "KDW"+e.coinCategoryDto.price;

                coinProductListBox.appendChild(coinProductListWrapper);
            }
        });

    }
    function loadShopItemList(){
        const url = "/mFollow/loadShopItemList?user_pk="+user_pk;

        fetch(url)
        .then(response => response.json())
        .then((response) =>{
            const shopLvItemListBox = document.querySelector("#shopLvItemListBox");
            const shopUseItemListBox = document.querySelector("#shopUseItemListBox");
            shopLvItemListBox.innerHTML ="";
            shopUseItemListBox.innerHTML = "";
            for(e of response.data){
                if(e.itemShopDto.item_category == '1'){
                    const shopLvItemListWrapper = document.querySelector("#shopLvItemListTemplete .shopLvItemListWrapper").cloneNode(true);
                    const itemLV = shopLvItemListWrapper.querySelector(".itemLV");
                    itemLV.innerText = "Lv."+e.itemCount;//바꿔줘야함 값 유저한테 가지고와서

                    const itemImg = shopLvItemListWrapper.querySelector(".itemImg");
                    itemImg.src = e.itemShopDto.item_img;
                    itemImg.setAttribute("onclick" , "loadItemInfo("+e.itemShopDto.item_pk+")");

                    const itemPrice = shopLvItemListWrapper.querySelector(".itemPrice");
                    const myLv = Math.pow(2, e.itemCount+1);
                    const myPrice = (e.itemShopDto.price * myLv)-e.itemShopDto.price;
                    
                    itemPrice.innerText = myPrice;
                    
                    itemPrice.setAttribute("onclick" , "openItemOrderBox("+e.itemShopDto.item_pk+")");
                    
                    shopLvItemListBox.appendChild(shopLvItemListWrapper);

                }else if(e.itemShopDto.item_category == '2'){
                    const shopUseItemListWrapper = document.querySelector("#shopUseItemListTemplete .shopUseItemListWrapper").cloneNode(true);
                    const haveItemCount = shopUseItemListWrapper.querySelector(".haveItemCount");
                    haveItemCount.innerText = e.itemCount+"개";//갯수 가지고와서 엮어줘야함.

                    const itemImg = shopUseItemListWrapper.querySelector(".itemImg");
                    itemImg.src = e.itemShopDto.item_img;
                    itemImg.setAttribute("onclick" , "loadItemInfo("+e.itemShopDto.item_pk+")");

                    const itemPrice = shopUseItemListWrapper.querySelector(".itemPrice");
                    itemPrice.innerText = e.itemShopDto.price;
                    itemPrice.setAttribute("onclick" , "openItemOrderBox("+e.itemShopDto.item_pk+")");

                    shopUseItemListBox.appendChild(shopUseItemListWrapper);
                    
                }
            }
        });

    }
    function loadCoinInfo(coin_category_pk){
        const url = "/mFollow/loadCoinInfo?coin_category_pk="+coin_category_pk;
        fetch(url)
        .then(response => response.json())
        .then((response) =>{
            const coinOrderBox = document.querySelector("#coinOrderBox");
            coinOrderBox.classList.remove("d-none");

            const payProduct = document.querySelector("#payProduct");
            payProduct.innerText = "고기 보석"+response.data.coin_count+"개 + "+response.data.bonus_coin+"개";

            const payAmount = document.querySelector("#payAmount");
            payAmount.innerText = response.data.price;

            const coinImg = document.querySelector("#coinImg");
            coinImg.src = response.data.coin_img;

            const goPayRequestPage = document.querySelector("#goPayRequestPage");
            goPayRequestPage.setAttribute("onclick","location.href = '/mFollow/payRequestPage?user_pk="+user_pk+"&coin_category_pk="+coin_category_pk+"'");
        });
        
    }

    
    function loadItemInfo(item_pk){
        const url = "/mFollow/loadItemInfo?item_pk="+item_pk;
        fetch(url)
        .then(response => response.json())
        .then((response) =>{
              
            const itemExplainName = document.querySelector("#itemExplainName");
            itemExplainName.innerText = response.data.name;

            const itemExplainDetail = document.querySelector("#itemExplainDetail");
            itemExplainDetail.innerText = response.data.item_explain;
            itemExplainDetail.classList.add("me-2")

            const itemExplainImg = document.querySelector("#itemExplainImg");
            itemExplainImg.src = response.data.item_img;

        });
    }
    let orderItemCateory = null;
    function orderItemInfo(item_pk){
        const url = "/mFollow/loadItemInfo?item_pk="+item_pk;
        fetch(url)
        .then(response => response.json())
        .then((response) =>{
             
            const orderItemName = document.querySelector("#orderItemName");
            orderItemName.innerText = response.data.name;

            const orderItemExplain = document.querySelector("#orderItemExplain");
            orderItemExplain.innerText = response.data.item_explain;
            
            const orderItemImg = document.querySelector("#orderItemImg");
            orderItemImg.src = response.data.item_img;

            const orderItemPrice = document.querySelector("#orderItemPrice");
            orderItemPrice.innerText = response.data.price;

            const orderItembutton = document.querySelector("#orderItembutton");
            orderItembutton.setAttribute("onclick" ,"insertOrderItem("+response.data.item_pk+")");
            const downButton  = document.querySelector("#downButton");
            downButton.setAttribute("onclick", "downQuantity("+response.data.price+")" );

            const upButton  = document.querySelector("#upButton");
            upButton.setAttribute("onclick", "upQuantity("+response.data.price+")" );


            orderItemCateory = response.data.item_category;
        });
    }
    function insertOrderItem(item_pk){
        const quantity = document.querySelector("#quantity").value;

        const url = "/mFollow/insertOrderItem";
        fetch(url, {
            method: "post",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "user_pk="+user_pk+"&item_pk="+item_pk+"&quantity="+quantity
        })
        .then(response => {
            loadShopItemList();
            getMyCoin();
            closeItemOrderBox();
        });
        
    }

    function upQuantity(defaltPrice){
        const orderItemPrice = document.querySelector("#orderItemPrice");
        const totalPrice = parseInt(orderItemPrice.innerText);
        const quantity = document.querySelector("#quantity");
        const itemQuantity = parseInt(quantity.value);
        if(orderItemCateory == 1 && itemQuantity == 10){
            return;
        }else{
            quantity.value = itemQuantity+1;
            if(orderItemCateory ==1){
                orderItemPrice.innerText = totalPrice * 2
            }else if(orderItemCateory == 2){
                orderItemPrice.innerText = defaltPrice * (itemQuantity+1)
            }
        }
            
    }

    function downQuantity(defaltPrice){
        const orderItemPrice = document.querySelector("#orderItemPrice");
        const totalPrice = parseInt(orderItemPrice.innerText);
        const quantity = document.querySelector("#quantity");
        const itemQuantity = parseInt(quantity.value);
        if(itemQuantity != 1){
            if(orderItemCateory == 1){
                orderItemPrice.innerText = totalPrice / 2
            }else if(orderItemCateory==2){
                orderItemPrice.innerText = defaltPrice * (itemQuantity-1)
            }
            quantity.value = itemQuantity-1;
        }
        
    }

    function openItemOrderBox(item_pk){
        orderItemInfo(item_pk);
        
        const itemOrderBox = document.querySelector("#itemOrderBox");
        itemOrderBox.classList.remove("d-none");                
    }
    function closeItemOrderBox(){
        const quantity = document.querySelector("#quantity");
        quantity.value = 1;

        const itemOrderBox = document.querySelector("#itemOrderBox");
        itemOrderBox.classList.add("d-none");                
    }
    
    function closeCoinOrderBox(){
        const coinOrderBox = document.querySelector("#coinOrderBox");
        coinOrderBox.classList.add("d-none");
    }
    function goAthorShop(){
        const itemShopButton = document.querySelector("#itemShopButton");
        itemShopButton.click();
        closeItemOrderBox();
        closeCoinOrderBox();
    }
    
    window.addEventListener("DOMContentLoaded", () =>{
        loadCoinProductList();
        getUrlKey();
    });
