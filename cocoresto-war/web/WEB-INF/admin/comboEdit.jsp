<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                    <section class="tile">

                        <div class="tile-header dvd dvd-btm bg-greensea">
                            <h1 class="custom-font"><strong>Commandes</strong></h1>
                        </div>
                        <form action="FrontController?option=combo" method="post">
                            <div class="tile-body">
                                <input type="hidden" value="${combo.id}" name="id" /> 
                                <div class="form-group">
                                    <label for="name">Nom du menu :</label>
                                    <input type="text" class="form-control" id="name" name="nameCombo" value="${combo.name}">
                                </div>

                                <div class="form-group">
                                    <label for="comboCategory">Catégorie :</label>
                                    <select id="comboCategory" name="comboCategory">
                                        <c:forEach var="category" items="${categories}" varStatus="loop"> 
                                            <option name="category" value="${category.id}" <c:if test="${combo.category.name eq category.name}">selected</c:if>>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div> 


                                <div class="form-group">
                                    <label for="comboDish1">Plat 1 :</label>
                                    <select id="comboDish1" name="comboDish1">
                                        <option name="listDish1" value=""></option>
                                        <c:forEach var="listDish1" items="${dishes}" varStatus="loop"> 
                                            <c:choose>
                                                <c:when test="${dish1.name eq listDish1.name}">
                                                    <option name="listDish1" value="${listDish1.id}" selected>${listDish1.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option name="listDish1" value="${listDish1.id}">${listDish1.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDish2">Plat 2 :</label>
                                    <select id="comboDish2" name="comboDish2">
                                        <option name="listDish2" value=" "></option>
                                        <c:forEach var="listDish2" items="${dishes}" varStatus="loop"> 
                                            <c:choose>
                                                <c:when test="${dish2.name eq listDish2.name}">
                                                    <option name="listDish2" value="${listDish2.id}" selected>${listDish2.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option name="listDish2" value="${listDish2.id}">${listDish2.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDish3">Plat 3 :</label>
                                    <select id="comboDish3" name="comboDish3">
                                        <option name="listDish3" value=""></option>
                                        <c:forEach var="listDish3" items="${dishes}" varStatus="loop"> 
                                            <c:choose>
                                                <c:when test="${dish3.name eq listDish3.name}">
                                                    <option name="listDish3" value="${listDish3.id}" selected>${listDish3.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option name="listDish3" value="${listDish3.id}">${listDish3.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboPrice">Prix :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="comboPrice" name="comboPrice" required value="${combo.price.price}">
                                        <div class="input-group-addon">euros</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="comboTax">Taxe :</label>
                                    <select id="comboTax" name="comboTax">
                                        <c:forEach var="tax" items="${taxes}" varStatus="loop"> 
                                            <option name="tax" value="${tax.id}" <c:if test="${combo.tax.id == tax.id}">selected</c:if>>${tax}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDiscount">Promotion :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="comboDiscount" name="comboDiscount" value="${combo.discount.rate}">
                                        <div class="input-group-addon">%</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="comboBeginDiscount">Date de début :</label>
                                    <input type="text" class="form-control" id="comboBeginDiscount" name="comboBeginDiscount" value="<fmt:formatDate value="${combo.discount.beginDate}" pattern="dd-MM-yyyy"/>" placeholder="jj-mm-aaaa">
                                </div>
                                <div class="form-group">
                                    <label for="comboEndDiscount">Date de fin :</label>
                                    <input type="text" class="form-control" id="comboEndDiscount" name="comboEndDiscount" value="<fmt:formatDate value="${combo.discount.endDate}" pattern="dd-MM-yyyy" />" placeholder="jj-mm-aaaa">
                                </div>
                            </div>
                            <div class="tile-footer dvd dvd-top">
                                <div class="row">
                                    <div class="col-xs-12 text-right">
                                        <a href="FrontController?option=combo" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </section>

