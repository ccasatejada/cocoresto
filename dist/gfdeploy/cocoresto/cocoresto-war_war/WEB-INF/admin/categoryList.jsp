
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

                    <section class="tile">

                        <div class="tile-header dvd dvd-btm bg-greensea">
                            <h1 class="custom-font"><strong>Catégories</strong></h1>
                            <ul class="controls">
                                <li>
                                    <a href="FrontController?option=category&task=edit" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
                                </li>
                            </ul>
                        </div>

                        <div class="tile-body p-0">
                            <table class ="table table-striped">
                                <thead>
                                    <tr class="bg-slategray">
                                        <th hidden>Id</th>
                                        <th>Catégorie</th>
                                        <th>Type</th>
                                        <th>Actions</th>                                       
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="category" items="${categories}" varStatus="loop">
                                        <tr>
                                            <td hidden>${category.id}</td>
                                            <td>${category.name}</td>
                                            <td>${category.type}</td>
                                            <td>
                                                <a href="FrontController?option=category&task=edit&id=${category.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Modifier</span></a>
                                                <a href="FrontController?option=category&task=delete&id=${category.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        ${pagination}
                    </section>

