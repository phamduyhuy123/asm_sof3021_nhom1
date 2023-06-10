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
                            title: "Failed",
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
        setTimeout(function () {
            if (!id) {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed',
                    text: 'Thêm vào giỏ hàng thất bại',
                    footer: ''
                })
                return;
            }
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

        }, 500);


    }
});
app.controller("cartPageController", function ($scope, $http, $rootScope, $cart, $calculate,$q) {
    $scope.selectedProduct = {};
    $scope.hasSelected = false;
    $scope.selectAllProduct=false;
    $scope.updateSelected = function () {
        $scope.hasSelected = false;
        for (var id in $scope.selectedProduct) {
            if ($scope.selectedProduct[id]) {
                $scope.hasSelected = true;
                break;
            }
        }
    };
    $scope.updateAllProductSelected=function () {
        $scope.selectAllProduct=!$scope.selectAllProduct;
        if($scope.selectAllProduct){
            angular.forEach($scope.itemsCart, function(item) {
                $scope.selectedProduct[item.id] = $scope.selectAllProduct;
            });
            $scope.hasSelected = true;
        }else {
            angular.forEach($scope.itemsCart, function(item) {
                $scope.selectedProduct[item.id] = $scope.selectAllProduct;
            });
            $scope.hasSelected = false;
        }


    }
    $scope.deleteMultipleItemFromCart = function() {
        Swal.fire({
            title: 'Bạn có muốn xóa sản phẩm đã chọn khỏi giỏ hàng',
            confirmButtonText: 'Xóa',
            showCancelButton: true,
            icon: 'question'
        }).then((result) => {
            if (result.isConfirmed) {
                let selectedProducts = [];
                for (var id in $scope.selectedProduct) {
                    if ($scope.selectedProduct[id]) {
                        selectedProducts.push(id);
                    }
                }
                let deletePromises = selectedProducts.map(function(id) {
                    console.log(id);
                    return $http.delete('/product/api/cart/remove/' + id)
                        .then(function(response) {
                            return response.data;
                        });
                });

                let lastPromise=$q.all(deletePromises)
                    .then(function(results) {
                        return results;
                    })
                    .catch(function(error) {
                        console.error(error);
                        throw error;
                    });
                lastPromise.then(function(results) {
                    $cart.setCartItems(results);
                    window.location.href="/cart"
                })
                    .catch(function(error) {
                        Swal.fire({
                            icon: 'error',
                            title: "Failed",
                            text: 'Xóa sản phẩm thất bại',
                            footer: ''
                        })
                    });
            }
        })


    };
    $scope.itemAmount={};
    $scope.increaseValue = function (id,amount) {
        let promise=$cart.addItem(id,amount);
        promise.then((response) => {
            if(response===true){

            }else {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed',
                    text: response.message,
                    footer: ''
                })
                $scope.amountPdetail=1;
            }
        });
    };
    $scope.decreaseValue = function (id,amount) {
        $http.put('/product/api/cart/decrease/'+id+'?amount='+amount).then(
            (response)=>{
                $cart.setCartItems(response.data)
            },
            (reason)=>{
                $rootScope.deleteItemFromCart(id);
            }
        )
    };

    $scope.checkMinValue = function (id,amount) {

        if (amount < 1) {
            amount = 1;
        }
        let promise= $cart.updateCart(id,amount);
        promise.then((response) => {
            if(response===true){

            }else {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed',
                    text: response.message,
                    footer: ''
                })
                $scope.amountPdetail=1;
            }
        });
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
                    text: `Số lượng yêu cầu cho ${$scope.amountPdetail} không có sẵn`,
                    footer: ''
                })
                $scope.amountPdetail = reason.data.error;
            });
    };
    $scope.addToCartProductDetail = function (event) {
        let id = event.target.getAttribute('data-product-id');
        let promise = $cart.addItem(id, $scope.amountPdetail);
        promise.then((response) => {
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
app.controller("registerController", function ($scope, $http, $rootScope) {
    $scope.user={
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        role: 'USER'
    }
    $scope.errors = {
        username: {},
        email: {},
        password: {},
        confirmPassword: {}
    };

    $scope.registerUserAction = function (){

        $scope.errors = {
            username: {},
            email: {},
            password: {},
            confirmPassword: {}
        };

        $http.post('/perform/register', $scope.user ).then(
            (response) => {
                if (response.data ) {
                    Swal.fire(
                        'Success',
                        'Đăng ký thành công',
                        'success'
                    )
                    setTimeout(()=>{
                        window.location.href = "/login";
                    },3000)
                }
            },
            (reason)=>{
                console.log("Unexpected response:", reason);
                console.log("Unexpected response:", reason.data);
                if(!reason.data){
                    Swal.fire({
                        icon: 'error',
                        title: 'Registration failed',
                        text: 'Đăng ký thất bại với lỗi không xác định',
                        footer: ''
                    })
                }else
                    for (var key in reason.data) {
                        if (reason.data.hasOwnProperty(key)) {
                            $scope.errors[key] = {
                                message: reason.data[key].split(', ')
                            };
                        }
                    }
            }
        );

    };
});
app.service('$cart', function ($http, $rootScope) {
    var cartItems = [];
    return {
        setCartItems: function (items) {
            cartItems = items;
            $rootScope.itemsCart=cartItems;
            $rootScope.itemsInCart=cartItems.length;
        },
        getCart: function () {
            return cartItems;
        },
        addItem: async function (id, amount) {
            return await $http.post("/product/api/cart/add/" + id + "/" + amount).then(
                (response)=> {
                    cartItems = response.data;
                    $rootScope.itemsCart=cartItems;
                    $rootScope.itemsInCart=cartItems.length;
                    return true;
                },
                (reason)=> {

                    cartItems = reason.data

                    console.log('vuot qua so luong')
                    $rootScope.itemsCart = cartItems;
                    $rootScope.itemsInCart = cartItems.length;
                    return {
                        error: true,
                        message: 'Không thể tăng thêm số lượng vì đã chạm đến giới hạn hàng trong kho'
                    };
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
        },
        finItemById: function (id) {
            return cartItems.find(value => value.id===id);
        },
        updateCart: async function (id, amount) {
            return await $http.put("/product/api/cart/update/" + id+'?amount='+amount).then(
                (response)=> {
                    cartItems = response.data
                    console.log('khong vuot qua so luong')
                    $rootScope.itemsCart = cartItems;
                    $rootScope.itemsInCart = cartItems.length;

                    return true;
                },
                (reason)=> {
                    console.log(reason.data)
                    cartItems = reason.data

                    $rootScope.itemsCart = cartItems;
                    $rootScope.itemsInCart = cartItems.length;
                    return {
                        error: true,
                        message: `Số lượng yêu cầu cho ${amount} không có sẵn`
                    };
                }
            );

        }
    }


});
app.service('$calculate', function () {
    this.calculatePriceDiscount = function (price, discount) {
        return parseInt(price - (price * discount / 100))
    }
});