<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Panneau d'administration : Serveur</title>
    </head>
    <body>
        <div class="container-fluid">

            <h1>Bonjour ${name}</h1>

            ${alert}
            

            <a href="#" class="btn btn-success">Nouvelle commande</a>

            <table class="table">
                <caption>Commandes en cours</caption>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Numéro</th>
                        <th>Table</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="active">
                        <th scope="row">1</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr class="success">
                        <th scope="row">3</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr>
                        <th scope="row">4</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr class="info">
                        <th scope="row">5</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr>
                        <th scope="row">6</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr class="warning">
                        <th scope="row">7</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr>
                        <th scope="row">8</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                    <tr class="danger">
                        <th scope="row">9</th>
                        <td>Column content</td>
                        <td>Column content</td>
                        <td>Column content</td>
                    </tr>
                </tbody>
            </table>


        </div>
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>