package controllers;

import entities.Category;
import entities.Discount;
import entities.Drink;
import entities.Format;
import entities.Price;
import entities.Tax;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanDrink;
import models.beanFormat;
import models.beanPrice;
import models.beanRate;

public class drinkController implements IController {

    private beanDrink bDrink = new beanDrink();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        boolean logged = false;
        Long groupId = 0L;

        Drink drink;
        Category category;
        Tax tax;
        Discount discount;
        Price price;
        Format format;

        ArrayList<Format> formats;
        ArrayList<Category> categories;
        ArrayList<Discount> discounts;
        ArrayList<Tax> taxes;
        ArrayList<Price> prices;

        boolean isDrinkDiscount;

        bDrink = (beanDrink) session.getAttribute("bDrink");
        beanRate bRate = (beanRate) session.getAttribute("bRate");
        beanPrice bPrice = (beanPrice) session.getAttribute("bPrice");
        beanFormat bFormat = (beanFormat) session.getAttribute("bFormat");

        if (bDrink == null) {
            bDrink = new beanDrink();
            drink = new Drink();
            category = new Category();
            format = new Format();
            price = new Price();
            tax = new Tax();
            discount = new Discount();
            drink.setFormat(format);
            bDrink.setDrink(drink);
            session.setAttribute("bDrink", bDrink);
        } else {
            drink = bDrink.getDrink();
            category = bDrink.getCategory();
            tax = bDrink.getTax();
            discount = bDrink.getDiscount();
        }
        if (bRate == null) {
            bRate = new beanRate();
            drink = new Drink();
            category = new Category();
            session.setAttribute("bRate", bRate);
        }
        if (bPrice == null) {
            bPrice = new beanPrice();
            drink = new Drink();
            category = new Category();
            session.setAttribute("bPrice", bPrice);
        }
        if (bFormat == null) {
            bFormat = new beanFormat();
            session.setAttribute("bFormat", bFormat);
        }

        formats = bDrink.findFormats();
        categories = bDrink.findCategories();
        discounts = bRate.findAllDiscounts();
        taxes = bRate.findAllTaxes();
        prices = bPrice.findAll();

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }

        if (logged && groupId >= 3) {

            if ("edit".equals(request.getParameter("task"))) {
                session.setAttribute("formats", formats);
                session.setAttribute("categories", categories);
                session.setAttribute("discounts", discounts);
                session.setAttribute("taxes", taxes);
                session.removeAttribute("drink");
                session.removeAttribute("isDrinkDiscount");
                return "/WEB-INF/admin/drinkEdit.jsp";
            }

            if ("modify".equals(request.getParameter("task"))) {
                drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
                session.setAttribute("category", drink.getCategory());
                session.setAttribute("formats", formats);
                session.setAttribute("categories", categories);
                session.setAttribute("discounts", discounts);
                session.setAttribute("taxes", taxes);
                session.setAttribute("drink", drink);
                //session.setAttribute("price", drink.getPrice());
                isDrinkDiscount = true;
                session.setAttribute("isDrinkDiscount", isDrinkDiscount);
                return "/WEB-INF/admin/drinkEdit.jsp";
            }

            if ("deleteDiscountDrink".equals(request.getParameter("task"))) {
                drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
                drink.setDiscount(null);
                bDrink.update(drink);
                session.setAttribute("drink", drink);
                session.removeAttribute("isDrinkDiscount");
                request.setAttribute("alert", Alert.setAlert("Succès", "Le discount a bien été détaché", "success"));
                return "/WEB-INF/admin/drinkEdit.jsp";
            }

            if ("delete".equals(request.getParameter("task"))) {
                drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
                drink.setActive(false);
                session.removeAttribute("isDrinkDiscount");
                try {
                    bDrink.delete(drink);
                    request.setAttribute("alert", Alert.setAlert("Succès", "La boisson a été supprimée", "success"));
                } catch (EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette boisson n'existe pas", "danger"));
                }
            }

            if (request.getParameter("cancelIt") != null) {
                session.removeAttribute("drink");
                session.removeAttribute("category");
                session.removeAttribute("formats");
                session.removeAttribute("discounts");
                session.removeAttribute("taxes");
                session.removeAttribute("categories");
                session.removeAttribute("isDrinkDiscount");
            }

            if (request.getParameter("createIt") != null) {
                drink = new Drink();
                drink.setActive(true);

                if (!"empty".equals(request.getParameter("comboDiscount"))) {
                    for (Discount di : discounts) {
                        if (di.getId().equals(Long.valueOf(request.getParameter("comboDiscount")))) {
                            discount = di;
                            drink.setDiscount(discount);
                            break;
                        }
                    }
                }
                for (Tax ta : taxes) {
                    if (ta.getRate().equals(Double.valueOf(request.getParameter("comboTax")))) {
                        tax = ta;
                        break;
                    }
                }
                for (Category ca : categories) {
                    if (ca.getName().equals(request.getParameter("comboCategory"))) {
                        category = ca;
                        break;
                    }
                }
                drink.setTax(tax);
                drink.setCategory(category);
                drink.setDescription(request.getParameter("description"));
                drink.setName(request.getParameter("name"));
                drink.setInventory(Integer.valueOf(request.getParameter("inventory")));
                drink.setImage(request.getParameter("image"));
                for (Format fo : formats) {
                    if (fo.getId().equals(Long.valueOf(request.getParameter("comboFormat")))) {
                        drink.setFormat(fo);
                        break;
                    }
                }
                price = new Price();
                price.setPrice(Double.valueOf(request.getParameter("price")));
                for (Price p : prices) {
                    if (p.getPrice().equals(price.getPrice())) {
                        price = p;
                        drink.setPrice(price);
                        break;
                    } 
                }
                if (price.getId() == null) {
                    price = new Price();
                    price.setPrice(Double.valueOf(request.getParameter("price")));
                    bPrice.create(price);
                    price = bPrice.findLastInserted();
                    drink.setPrice(price);
                }

                bDrink.create(drink);
                session.setAttribute("drink", drink);
                session.removeAttribute("isDrinkDiscount");
                request.setAttribute("alert", Alert.setAlert("Succès", "La boisson a été ajoutée", "success"));
            }

            if (request.getParameter("modifyIt") != null) {
                drink = (Drink) session.getAttribute("drink");
                if (!"empty".equals(request.getParameter("comboDiscount"))) {
                    for (Discount di : discounts) {
                        if (di.getId().equals(Long.valueOf(request.getParameter("comboDiscount")))) {
                            discount = di;
                            drink.setDiscount(discount);
                            break;
                        }
                    }
                }
                for (Tax ta : taxes) {
                    if (ta.getRate().equals(Double.valueOf(request.getParameter("comboTax")))) {
                        tax = ta;
                        break;
                    }
                }
                for (Category ca : categories) {
                    if (ca.getName().equals(request.getParameter("comboCategory").replaceAll("éèà", "eea"))) {
                        category = ca;
                        break;
                    }
                }
                drink.setDiscount(discount);
                drink.setTax(tax);
                drink.setCategory(category);
                drink.setDescription(request.getParameter("description"));
                drink.setName(request.getParameter("name"));
                drink.setInventory(Integer.valueOf(request.getParameter("inventory")));
                if (request.getParameter("image") != null) {
                    drink.setImage(request.getParameter("image"));
                }
                for (Format fo : formats) {
                    if (fo.getId().equals(Long.valueOf(request.getParameter("comboFormat")))) {
                        drink.setFormat(fo);
                        break;
                    }
                }
                price = new Price();
                price.setPrice(Double.valueOf(request.getParameter("price")));
                System.out.println("Prix HT : " + request.getParameter("price"));
                for (Price p : prices) {
                    if (p.getPrice().equals(price.getPrice())) {
                        price = p;
                        drink.setPrice(price);
                        break;
                    }
                }
                if (price.getId() == null) {
                    price = new Price();
                    price.setPrice(Double.valueOf(request.getParameter("price")));
                    bPrice.create(price);
                    price = bPrice.findLastInserted();
                    drink.setPrice(price);
                }

                bDrink.update(drink);
                session.setAttribute("drink", drink);
                session.removeAttribute("isDrinkDiscount");
                request.setAttribute("alert", Alert.setAlert("Succès", "La boisson a été mise à jour", "success"));
            }

            if ("attachDiscount".equals(request.getParameter("task"))) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                discount.setRate(Double.valueOf(request.getParameter("amount")));
                try {
                    discount.setBeginDate(formatter.parse(request.getParameter("beginDate")));
                    discount.setEndDate(formatter.parse(request.getParameter("endDate")));
                } catch (ParseException ex) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Les dates n'ont pas été rentrées correctement", "danger"));
                }
                bRate.create(discount);
                drink.setDiscount(discount);
                bDrink.update(drink);
                session.removeAttribute("isDrinkDiscount");
                request.setAttribute("alert", Alert.setAlert("Succès", "Le discount a été attaché à la boisson", "success"));
                return "/WEB-INF/admin/drinkEdit.jsp";
            }

            if ("drink".equals(request.getParameter("option"))) {
                session.removeAttribute("isDrinkDiscount");
                getList(request, "option=drink");

                return "/WEB-INF/admin/drinkList.jsp";
            }

        } else {
            try {
                // not logged or wrong groupId
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/admin/drinkEdit.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void getList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bDrink.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<Drink> drinks = bDrink.findAllByRange(pagination.getMin(), 10);

        request.setAttribute("drinks", drinks);
    }

}
