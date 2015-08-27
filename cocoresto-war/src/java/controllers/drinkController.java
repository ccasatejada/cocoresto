package controllers;

import entities.Category;
import entities.Discount;
import entities.Drink;
import entities.Format;
import entities.Price;
import entities.Tax;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanDrink;
import models.beanPrice;
import models.beanRate;

public class drinkController implements IController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

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
        Format format;

        beanDrink bDrink = (beanDrink) session.getAttribute("bDrink");
        beanRate bRate = (beanRate) session.getAttribute("bRate");
        beanPrice bPrice = (beanPrice) session.getAttribute("bPrice");

        if (bDrink == null) {
            bDrink = new beanDrink();
            drink = new Drink();
            category = new Category();
            formats = new ArrayList();
            format = new Format();
            price = new Price();
            tax = new Tax();
            discount = new Discount();
            drink.setFormats(formats);
            bDrink.setDrink(drink);
            session.setAttribute("bDrink", bDrink);
        } else {
            drink = bDrink.getDrink();
            format = bDrink.getFormat();
            category = bDrink.getCategory();
            price = bDrink.getPrice();
            tax = bDrink.getTax();
            discount = bDrink.getDiscount();
        }

        if (bRate == null) {
            bRate = new beanRate();
            drink = new Drink();
            format = new Format();
            category = new Category();
            session.setAttribute("bRate", bRate);
        }

        if (bPrice == null) {
            bPrice = new beanPrice();
            price = new Price();
            drink = new Drink();
            format = new Format();
            category = new Category();
            session.setAttribute("bPrice", bPrice);
        }

        formats = bDrink.findFormats();
        categories = bDrink.findCategories();
        discounts = bRate.findAllDiscounts();
        taxes = bRate.findAllTaxes();
        prices = bPrice.findAll();

        if ("edit".equals(request.getParameter("task"))) {
            session.setAttribute("formats", formats);
            session.setAttribute("categories", categories);
            session.setAttribute("discounts", discounts);
            session.setAttribute("taxes", taxes);
            session.removeAttribute("drink");
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

            return "/WEB-INF/admin/drinkEdit.jsp";
        }

        if ("deleteDiscountDrink".equals(request.getParameter("task"))) {
            drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
            drink.setDiscount(null);
            session.setAttribute("drink", drink);

            return "/WEB-INF/admin/drinkEdit.jsp";
        }

        if ("delete".equals(request.getParameter("task"))) {
            drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
            drink.setActive(false);
            bDrink.delete(drink);
        }

        if (request.getParameter("cancelIt") != null) {
            session.removeAttribute("drink");
            session.removeAttribute("category");
            session.removeAttribute("formats");
            session.removeAttribute("discounts");
            session.removeAttribute("taxes");
            session.removeAttribute("categories");
        }

        if (request.getParameter("createIt") != null) {
            drink.setActive(true);
            formats = (ArrayList<Format>) session.getAttribute("formats");
            for (Format fo : formats) {
                if (fo.getName().equals(request.getParameter("comboFormat"))) {
                    format.setId(fo.getId());
                    format.setName(request.getParameter("comboFormat"));
                    break;
                }
            }
            for (Discount di : discounts) {
                if (di.getId().equals(Long.valueOf(request.getParameter("idDiscount")))) {
                    discount = bRate.findDiscountById(di.getId());
                    break;
                }
            }
            for (Tax ta : taxes) {
                if (ta.getId().equals(Long.valueOf(request.getParameter("idTax")))) {
                    tax = bRate.findTaxById(ta.getId());
                    break;
                }
            }
            drink.setDiscount(discount);
            drink.setTax(tax);
            drink.setFormats(formats);
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

        }

        if (request.getParameter("modifyIt") != null) {
            drink = (Drink) session.getAttribute("drink");
            for (Format fo : formats) {
                if (fo.getName().equals(request.getParameter("comboFormat"))) {
                    format.setId(fo.getId());
                    format.setName(request.getParameter("comboFormat"));
                    break;
                }
            }
            for (Discount di : discounts) {
                if (di.getId().equals(Long.valueOf(request.getParameter("idDiscount")))) {
                    discount = bRate.findDiscountById(di.getId());
                    break;
                }
            }
            for (Tax ta : taxes) {
                if (ta.getId().equals(Long.valueOf(request.getParameter("idTax")))) {
                    tax = bRate.findTaxById(ta.getId());
                    break;
                }
            }
            drink.setDiscount(discount);
            drink.setTax(tax);
            drink.setFormats(formats);
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
            session.setAttribute("drink", drink);
        }

        if ("drink".equals(request.getParameter("option"))) {
            ArrayList<Drink> drinks = bDrink.findAll();
            session.setAttribute("drinks", drinks);
            return "/WEB-INF/admin/drinkList.jsp";
        }

        return "/WEB-INF/admin/drinkEdit.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
