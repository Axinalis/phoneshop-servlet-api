package com.es.phoneshop.web;

import static com.es.phoneshop.constant.ConstantStrings.*;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/PersonalDetailsServlet")
public class PersonalDetailsServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute(ORDER) == null){
            request.getSession().setAttribute(ORDER, orderService.getOrder(cartService.getCart(request)));
        }
        request.getRequestDispatcher("/WEB-INF/pages/personalDataForOrder.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute(ORDER) == null){
            request.getSession().setAttribute(ORDER, orderService.getOrder(cartService.getCart(request)));
        }
        Order order = (Order)request.getSession().getAttribute(ORDER);
        Map<String, String> errors = new HashMap<>();
        Map<String, String> values = new HashMap<>();
        LocalDate deliveryDate = null;
        PaymentMethodType paymentType = null;

        //Errors if value is null
        putNotNull(request, FIRST_NAME, errors, values);
        putNotNull(request, LAST_NAME, errors, values);
        putNotNull(request, PHONE_NUMBER, errors, values);
        putNotNull(request, DELIVERY_DATE, errors, values);
        putNotNull(request, ADDRESS, errors, values);
        putNotNull(request, PAYMENT_TYPE, errors, values);

        //Validating phone number, date and type of payment
        try{
            values.put(PHONE_NUMBER, Validator.parsingPhoneNumber(values.get(PHONE_NUMBER)));
        } catch(PersonalInfoParsingException ex) {
            errors.put(PHONE_NUMBER, ex.getMessage());
        }
        try{
            deliveryDate = Validator.parsingDate(values.get(DELIVERY_DATE));
        } catch(PersonalInfoParsingException ex) {
            errors.put(DELIVERY_DATE, ex.getMessage());
        }
        try{
            paymentType = Validator.parsingPaymentType(values.get(PAYMENT_TYPE));
        } catch(PersonalInfoParsingException ex) {
            errors.put(PAYMENT_TYPE, ex.getMessage());
        }

        //Set fields of order
        putNotError(FIRST_NAME, errors, order, values.get(FIRST_NAME));
        putNotError(LAST_NAME, errors, order, values.get(LAST_NAME));
        putNotError(PHONE_NUMBER, errors, order, values.get(PHONE_NUMBER));
        putNotError(DELIVERY_DATE, errors, order, deliveryDate);
        putNotError(ADDRESS, errors, order, values.get(ADDRESS));
        putNotError(PAYMENT_TYPE, errors, order, paymentType);

        if(errors.size() == 0){
            request.getRequestDispatcher("/WEB-INF/pages/finalCheckoutOrder.jsp").forward(request, response);
        } else {
            request.setAttribute(ERRORS, errors);
            request.setAttribute(VALUES, values);
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

    private void putNotError(String valueName, Map<String, String> errors, Order order, Object value){
        if(value == null){
            return;
        }
        if(!errors.containsKey(valueName)){
            switch(valueName){
                case FIRST_NAME:
                    order.setFirstName((value.getClass() == String.class) ? (String) value : null);
                case LAST_NAME:
                    order.setLastName((value.getClass() == String.class) ? (String) value : null);
                case PHONE_NUMBER:
                    order.setPhone((value.getClass() == String.class) ? (String) value : null);
                case DELIVERY_DATE:
                    order.setDeliveryDate((value.getClass() == LocalDate.class) ? (LocalDate) value : null);
                case ADDRESS:
                    order.setDeliveryAddress((value.getClass() == String.class) ? (String) value : null);
                case PAYMENT_TYPE:
                    order.setPaymentType((value.getClass() == PaymentMethodType.class) ? (PaymentMethodType) value : null);

            }

        }
    }
}
