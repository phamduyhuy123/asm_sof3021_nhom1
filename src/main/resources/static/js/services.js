var app = angular.module("myapp", []);
app.controller("homeCtrl", function ($scope, $http, $rootScope, $cart, $calculate) {
    $http.get("/product/api/cart").then(function (response) {
        if (response.data != null || response.data !== "") {
            $cart.setCartItems(response.data);
            $rootScope.itemsCart = $cart.getCart();
            $rootScope.itemsInCart = $cart.getCart().length;
        }
    }, function (response) {
        $rootScope.itemsCart = [];
        $rootScope.itemsInCart = 0;
    });
    $rootScope.itemsInCart;
    $rootScope.totalPriceFromCart = function () {
        let total = 0
        if ($rootScope.itemsCart) {

            for (let i = 0; i < $rootScope.itemsCart.length; i++) {
                if ($rootScope.itemsCart[i].disCount) {
                    total += Number(($rootScope.itemsCart[i].price - ($rootScope.itemsCart[i].price * $rootScope.itemsCart[i].disCount / 100)) * $rootScope.itemsCart[i].quantity);
                } else {
                    total += Number($rootScope.itemsCart[i].price * $rootScope.itemsCart[i].quantity);
                }
            }
        }
        return total;
    };
    $rootScope.calculateDisCount = function (price, disCount) {
        return $calculate.calculatePriceDiscount(price, disCount);
    }


});
app.controller("cartController", function ($scope, $http, $rootScope, $cart, $calculate) {
    $rootScope.itemsCart = $cart.getCart();
    $scope.getTotal = function () {
        let total = 0
        for (let i = 0; i < $scope.itemsCart.length; i++) {
            total += ($scope.itemsCart[i].price * $scope.itemsCart[i].quantity);
        }
        return total;
    }

    $rootScope.deleteItemFromCart = function (item) {
        Swal.fire({
            title: 'Bạn có muốn xóa sản phẩm này khỏi giỏ hàng',
            confirmButtonText: 'Xóa',
            showCancelButton: true,
            icon: 'question'
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                var isSuccess = $cart.removeItem(item);
                isSuccess.then((result)=>{
                    if(result){
                        Swal.fire('Delete', 'Sản phẩm đã được xóa khỏi giỏ hàng', 'success')
                    }else {
                        Swal.fire({
                            icon: 'error',
                            title: response.data.message,
                            text: 'Không tìm thấy sản phẩm',
                            footer: ''
                        })
                    }
                })
            }
        })


    }
    $rootScope.addToCart = function (event, amount) {
        let id = event.target.getAttribute('data-product-id');
        let isSuccess = $cart.addItem(id, amount);
        console.log(id)
        console.log(amount)
        console.log(isSuccess)
        isSuccess.then((response) => {
            if(response===true){
                Swal.fire(
                    'Success',
                    'Thêm vào giỏ hàng thành công',
                    'success'
                )
            }else {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed',
                    text: 'Thêm vào giỏ hàng thất bại',
                    footer: ''
                })
            }
        });

    }
});
app.controller("cartPageController", function ($scope, $http, $rootScope, $cart, $calculate) {
    $scope.increaseValue = function (id, amount) {
        let tempP=null;
        $cart.getCart().forEach((value, index) => {
            if(value.id===id){
                tempP=value;
            }
        });
        $http.get('/product/api/' + id + '/stock?amount=' + tempP.quantity).then(function (response) {
            if (response.data.error) {
                Swal.fire({
                    icon: 'error',
                    title: response.data.message,
                    text: 'Không thể tăng thêm số lượng vì đã chạm đến giới hạn hàng trong kho',
                    footer: ''
                })
                tempP.quantity=response.data.error;
            } else if (response.data.success) {
                tempP.quantity ++;
            }
        })

    };

    $scope.decreaseValue = function (id,amount) {

        if (amount > 1) {
            amount--;
        }



    };

    $scope.checkMinValue = function (id, amount) {
        let tempP=null;
        $cart.getCart().forEach((value, index) => {
            if(value.id===id){
                tempP=value;

            }
        });
        $http.get('/product/api/' + id + '/stock?amount=' + tempP.quantity).then(function (response) {
            if (response.data.error) {
                Swal.fire({
                    icon: 'error',
                    title: response.data.message,
                    text: 'Không thể tăng thêm số lượng vì đã chạm đến giới hạn hàng trong kho',
                    footer: ''
                })
                tempP.quantity=response.data.error;

            }

        })
    };

});
app.controller("productDetailController", function ($scope, $http, $rootScope, $cart, $calculate) {
    $scope.amountPdetail = 1;
    $scope.productId = angular.element('#productDetail').data('product-id');
    $scope.increaseValue = function () {
        $http.get('/product/api/stock?amount=' +$scope.amountPdetail+'&id='+$scope.productId).then(
            (response)=> {
                $scope.amountPdetail++;
            },
            (reason)=>{
                Swal.fire({
                    icon: 'error',
                    title: 'Increase quantity failed',
                    text: 'Không thể tăng thêm số lượng vì đã chạm đến giới hạn hàng trong kho',
                    footer: ''
                })
                console.log(reason.data.error)
                $scope.amountPdetai=reason.data.error;
            });

    };
    $scope.decreaseValue = function () {
        if ($scope.amountPdetail > 1) {
            $scope.amountPdetail--;
        }
    };

    $scope.checkMinValue = function () {
        if ($scope.amountPdetail < 1) {
            $scope.amountPdetail = 1;
        }
        $http.get('/product/api/stock?amount=' +$scope.amountPdetail+'&id='+$scope.productId).then(
            (response)=> {

            },
            (reason)=>{
                Swal.fire({
                    icon: 'error',
                    title: 'Increase quantity failed',
                    text: 'Không thể tăng thêm số lượng vì đã chạm đến giới hạn hàng trong kho',
                    footer: ''
                })
                $scope.amountPdetail = reason.data.error;
            });
    };
    $scope.addToCartProductDetail = function (event) {
        let id = event.target.getAttribute('data-product-id');
        let isSuccess = $cart.addItem(id, $scope.amountPdetail);
        isSuccess.then((response) => {
            if(response===true){
                console.log($scope.amountPdetail)
                Swal.fire(
                    'Success',
                    'Thêm vào giỏ hàng thành công',
                    'success'
                )

            }else {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed',
                    text: 'Thêm vào giỏ hàng thất bại',
                    footer: ''
                })
                $scope.amountPdetail=1;
            }
        });
        $scope.amountPdetail=1;
    }
});

app.service('$cart', function ($http, $rootScope) {
    var cartItems = [];
    return {
        setCartItems: function (items) {
            cartItems = items;
        },
        getCart: function () {
            return cartItems;
        },
        addItem: async function (id, amount) {
            console.log("addItem")
            return await $http.post("/product/api/cart/add/" + id + "/" + amount).then(
                (response)=> {
                    console.log(response)
                    cartItems = response.data
                    $rootScope.itemsCart = cartItems;
                    $rootScope.itemsInCart = cartItems.length;

                    return true;
                },
                (reason)=> {
                    console.log(reason)
                    cartItems = reason.data
                    $rootScope.itemsCart = cartItems;
                    $rootScope.itemsInCart = cartItems.length;
                    return false;
                }
                );

        },
        removeItem: async function (id) {
            return await $http.delete('/product/api/cart/remove/' + id)
                .then(
                    async (response) => {
                        cartItems = await response.data
                        $rootScope.itemsCart = cartItems;
                        $rootScope.itemsInCart = cartItems.length;
                        return true;
                    },
                    (reason) => {

                        return false;
                    }
                );
        },
        clearCart: function () {
            cartItems = [];
        }
    }


});
app.service('$calculate', function () {
    this.calculatePriceDiscount = function (price, discount) {
        return parseInt(price - (price * discount / 100))
    }
});