package controllers;

import entities.Category;
import entities.Discount;
import entities.Drink;
import entities.Employee;
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
            formats = new ArrayList();
            price = new Price();
            tax = new Tax();
            discount = new Discount();
            drink.setFormats(formats);
            bDrink.setDrink(drink);
            session.setAttribute("bDrink", bDrink);
        } else {
            drink = bDrink.getDrink();
            category = bDrink.getCategory();
            price = bDrink.getPrice();
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
            price = new Price();
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
                ArrayList<Format> uncheckedFormats = new ArrayList();
                for (Format fo : formats) {
                    if (!drink.getFormats().contains(fo)) {
                        uncheckedFormats.add(fo);
                    }
                }
                session.setAttribute("uncheckedFormats", uncheckedFormats);
                session.setAttribute("category", drink.getCategory());
                session.setAttribute("formats", formats);
                session.setAttribute("categories", categories);
                session.setAttribute("discounts", discounts);
                session.setAttribute("taxes", taxes);
                session.setAttribute("drink", drink);
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
                return "/WEB-INF/admin/drinkEdit.jsp";
            }

            if ("delete".equals(request.getParameter("task"))) {
                drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
                drink.setActive(false);
                bDrink.delete(drink);
                session.removeAttribute("isDrinkDiscount");
            }

            if (request.getParameter("cancelIt") != null) {
                session.removeAttribute("drink");
                session.removeAttribute("category");
                session.removeAttribute("formats");
                session.removeAttribute("discounts");
                session.removeAttribute("taxes");
                session.removeAttribute("categories");
                session.removeAttribute("uncheckedFormats");
                session.removeAttribute("isDrinkDiscount");
            }

            if (request.getParameter("createIt") != null) {
                drink = new Drink();
                drink.setActive(true);
                drink.setFormats(new ArrayList());
                for (int i = 0; i < formats.size(); i++) {
                    if (request.getParameter("formatsList" + i) != null) {
                        Long id = Long.valueOf(request.getParameter("formatsList" + i));
                        Format f = bFormat.findById(id);
                        drink.getFormats().add(f);
                    }
                }
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
                for (Price p : prices) {
                    if (p.getPrice().equals(Double.valueOf(request.getParameter("price")))) {
                        price = p;
                        break;
                    } else {
                        price = null;
                    }
                }
                if (price == null) {
                    price = new Price();
                    price.setPrice(Double.valueOf(request.getParameter("price")));
                    bPrice.create(price);
                    price = bPrice.findLastInserted();
                    drink.setPrice(price);
                } else {
                    drink.setPrice(price);
                }
                bDrink.create(drink);
                session.setAttribute("drink", drink);
                session.removeAttribute("isDrinkDiscount");
            }

            if (request.getParameter("modifyIt") != null) {
                drink = (Drink) session.getAttribute("drink");
                ArrayList<Format> uncheckedFormats = (ArrayList) session.getAttribute("uncheckedFormats");
                ArrayList<Format> drinkFormats = new ArrayList();
                for (Format fo : drink.getFormats()) {
                    drinkFormats.add(fo);
                }
                drink.setFormats(new ArrayList());
                for (int i = 0; i < uncheckedFormats.size(); i++) {
                    if (request.getParameter("listUncheck" + i) != null) {
                        Long id = Long.valueOf(request.getParameter("listUncheck" + i));
                        Format f = bFormat.findById(id);
                        drink.getFormats().add(f);
                    }
                }
                for (int i = 0; i < drinkFormats.size(); i++) {
                    if (request.getParameter("listCheck" + i) != null) {
                        Long id = Long.valueOf(request.getParameter("listCheck" + i));
                        Format f = bFormat.findById(id);
                        drink.getFormats().add(f);
                    }
                }
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
                drink.setImage(request.getParameter("image"));
                if (!drink.getPrice().getPrice().equals(Double.valueOf(request.getParameter("price")))) {
                    for (Price p : prices) {
                        if (p.getPrice().equals(Double.valueOf(request.getParameter("price")))) {
                            price = p;
                            break;
                        } else {
                            price = null;
                        }
                    }
                    if (price == null) {
                        price = new Price();
                        price.setPrice(Double.valueOf(request.getParameter("price")));
                        bPrice.create(price);
                        price = bPrice.findLastInserted();
                        drink.setPrice(price);
                    } else {
                        drink.setPrice(price);
                    }
                }
                bDrink.update(drink);
                session.setAttribute("uncheckedFormats", uncheckedFormats);
                session.setAttribute("drink", drink);
                session.removeAttribute("isDrinkDiscount");
            }

            if ("attachDiscount".equals(request.getParameter("task"))) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                discount.setRate(Double.valueOf(request.getParameter("amount")));
                try {
                    discount.setBeginDate(formatter.parse(request.getParameter("beginDate")));
                    discount.setEndDate(formatter.parse(request.getParameter("endDate")));
                } catch (ParseException ex) {
                    ex.getMessage();
                }
                bRate.create(discount);
                drink.setDiscount(discount);
                bDrink.update(drink);
                session.removeAttribute("isDrinkDiscount");
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
