angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1';
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
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token};

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
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.loadCart = function () {
        $http.get(contextPath + '/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.addToCart = function (id) {
        $http.get(contextPath + '/cart/add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function (id) {
        $http.get(contextPath + '/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }
    $scope.deleteFromCart = function (id) {
        $http.get(contextPath + '/cart/delete/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createOrder = function (address, phone) {
        $http.post(contextPath + '/orders/' + address + '/' + phone)
            .then(function (response) {
                $scope.loadCart();
            });

    }

    $scope.increase = function (id) {
        $http.get(contextPath + '/cart/inc/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.decrease = function (id) {
        $http.get(contextPath + '/cart/dec/' + id)
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
});