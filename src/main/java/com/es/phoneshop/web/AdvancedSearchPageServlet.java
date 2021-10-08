package com.es.phoneshop.web;

import com.es.phoneshop.comparator.filter.FilterCreator;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.exception.PersonalInfoParsingException;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;
import com.es.phoneshop.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/AdvancedSearchPageServlet")
public class AdvancedSearchPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        //Map<String, String> values = new HashMap<>();

        String typeOfSearch = request.getParameter(SEARCH_TYPE);
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        FilterCreator creator = new FilterCreator();

        try{
            minPrice = Validator.parsingPrice(request.getParameter(MIN_PRICE));
        } catch(ValidationException ex){
            errors.put(MIN_PRICE, ex.getMessage());
        }
        try{
            maxPrice = Validator.parsingPrice(request.getParameter(MAX_PRICE));
        } catch(ValidationException ex){
            errors.put(MAX_PRICE, ex.getMessage());
        }

        creator.setPriceSpan(minPrice, maxPrice);
        creator.setTypeOfSearch(typeOfSearch);
        creator.setQuery(request.getParameter(DESCRIPTION));

        if(errors.size() == 0){
            request.setAttribute("products", productDao.findProducts(creator.createFilter()));
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearchProductList.jsp").forward(request, response);
        } else {
            request.setAttribute(ERRORS, errors);
            //request.setAttribute(VALUES, values);
            doGet(request, response);
        }
    }

    private void putNotNull(HttpServletRequest request, String valueName, Map<String, String> errors, Map<String, String> values){
        String string = request.getParameter(valueName);
        if(string == null || string.equals("")){
            errors.put(valueName, "Value cannot be empty");
        } else {
            values.put(valueName, string);
        }
    }
}
