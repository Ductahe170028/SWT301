package controller.admin;

import dao.OrdersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Orders;

@WebServlet(name = "OrderHistoryServlet", urlPatterns = {"/orderhistory"})

public class OrderHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize DAO
        OrdersDAO od = new OrdersDAO();

        String strUid = request.getParameter("uid");
        List<Orders> listO;
        if (strUid == null || strUid.isEmpty()) {
            // Retrieve all orders if uid is null or empty
            listO = od.getAllOrders();
        } else {
            try {
                int uid = Integer.parseInt(strUid);
                listO = od.getOrderByUserID(uid);
            } catch (NumberFormatException e) {
                System.err.println("Invalid UID format: " + strUid);
                listO = new ArrayList<>();
            }
        }

// Set the list of orders as a request attribute
        request.setAttribute("listO", listO);

// Forward the request to the order-history.jsp page
        request.getRequestDispatcher("order-history.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String txt = request.getParameter("txt");
        OrdersDAO od = new OrdersDAO();
        List<Orders> list = od.getAllOrders();
        List<Orders> listO = new ArrayList<>();

        for (Orders o : list) {
            if (o.getUsername().contains(txt)) {
                listO.add(o);
            }
        }

        request.setAttribute("listO", listO);
        request.getRequestDispatcher("bill-manager.jsp").forward(request, response);

    }

}
