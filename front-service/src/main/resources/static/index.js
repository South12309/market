angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';
    const contextPathAuth = 'http://localhost:5555/auth/api/v1';
    if ($localStorage.marchMarketUser) {
        try {
            let jwt = $localStorage.marchMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.marchMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        if ($localStorage.marchMarketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
        }
    }

    $scope.tryToAuth = function () {
        $http.post(contextPathAuth + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token};
                    $scope.loadOrders();
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.marchMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.marchMarketUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.loadProducts = function () {
        $http.get(contextPathCore + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.loadOrders = function () {
        $http.get(contextPathCore + '/orders')
            .then(function (response) {
                $scope.orders = response.data;
            });
    };

    $scope.loadCart = function () {
        $http.get(contextPathCarts + '/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.addToCart = function (id) {
        $http.get(contextPathCarts + '/cart/add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function (id) {
        $http.get(contextPathCarts + '/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }
    $scope.deleteFromCart = function (id) {
        $http.get(contextPathCarts + '/cart/delete/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createOrder = function (address, phone) {
        $http.post(contextPathCore + '/orders/' + address + '/' + phone)
            .then(function (response) {
                alert("Заказ оформлен")
                $scope.loadCart();
                $scope.loadOrders();
            });

    }

    $scope.increase = function (id) {
        $http.get(contextPathCarts + '/cart/inc/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.decrease = function (id) {
        $http.get(contextPathCarts + '/cart/dec/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    // $scope.deleteProduct = function (id) {
    //     $http.delete(contextPath + '/products/' + id)
    //         .then(function (response) {
    //             $scope.loadProducts();
    //         });
    // }

    // $scope.createNewProduct = function () {
    //     $http.post(contextPath + '/products', $scope.newProduct)
    //         .then(function (response) {
    //             $scope.newProduct = null;
    //             $scope.loadProducts();
    //         });
    // }

    $scope.loadProducts();
    $scope.loadCart();
    $scope.loadOrders();
});