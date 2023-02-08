angular.module('market').controller('cartController', function ($scope, $http) {
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    $scope.loadCart = function () {
        $http.get(contextPathCarts + '/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

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



    $scope.loadCart();
});