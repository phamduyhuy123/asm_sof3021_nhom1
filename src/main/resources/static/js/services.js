app.service('$product',function ($http){
    var products=[];
    
    var productTemp;
    this.setProducts =function (products){
        this.products=products;
    }
    this.getAllProducts = function (){
        return this.products;
    }
    this.getProductFromLink=function (){
        return this.productTemp;
    }
    this.setProductById= function (id){
        this.products.forEach(e =>{
            if(e.id===id){

                this.productTemp=e;
            }
        });

    }
     this.getProductById=function (id){
        var product;
        this.products.forEach(e =>{
            if(e.id===id){
                product=e;

            }
        });
        return product;
    }

});
app.service('$cart',function(){
    var cartItems=[];
    return{
        getCart: function(){
            return cartItems;
        },
        addItem: function (item,amount) {
            var existingItem = cartItems.find(function (cartItem) {
                return cartItem.product.id === item.id;
            });
            if (existingItem) {
                existingItem.quantity+=amount;
            } else {
                cartItems.push({
                    product: item,
                    quantity: amount
                });
            }
            
        },
        removeItem: function (item) {
            var index = cartItems.findIndex(function (cartItem) {
                return cartItem.product.id === item.id;
            });
            if (index > -1) {
                cartItems.splice(index, 1);
            }
        },
        clearCart: function () {
            cartItems = [];
        }
    }
   

});
app.service('$calculate',function(){
    this.calculatePriceDiscount=function(price,discount ){
        return parseInt(price - (price * discount / 100))
    }
});