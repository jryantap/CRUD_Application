package dao;
/*DAO- data access object = classes with methods will interact with the database */

import java.sql.*;
import java.util.*;
import dbutil.DBUtil;
import pojo.Product;

public class ProductManagementDAO {

    //get all products method.  used List instead of ArrayList so it's better code management
    //incase we want to change productList ArrayList to a LinkedList, we don't have to change the jdbc code that much
    public List<Product> getAllProducts()
    {
        List<Product> productList = new ArrayList<Product>();
        try
        {
            //typical jdbc coding
            Connection conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM product");
            while(rs.next())
            {
                Product product = new Product(rs.getString("prod_id"), rs.getString("prod_name"), rs.getInt("prod_price"));
                productList.add(product);
            }
            DBUtil.closeConnection(conn);  //close connection
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return productList;
    }

    //different query
    public Product getProductByid(String productId)
    {
        Product product = null;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product WHERE prod_id = ?");
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            //iterate through result
            while(rs.next())
            {
                product = new Product(rs.getString("prod_id"), rs.getString("prod_name"), rs.getInt("prod_price"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return product;
    }

    public int addProduct(Product product)
    {
        //status displays 1 if successfully inserted data or error; successful or not
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO product VALUES(?,?,?)");
            //set parameters of query here but using the values for the product object
            ps.setString(1, product.getProductid());
            ps.setString(2, product.getProductName());
            ps.setInt(3, product.getProductPrice());
            status = ps.executeUpdate();  //if successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    //updates a product already in the table
    public int updateProduct(Product product)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE product SET prod_name=?, prod_price=? WHERE prod_id=?");
            //set parameters of query here but using the values for the product object
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getProductPrice());
            ps.setString(3, product.getProductid());
            status = ps.executeUpdate();  //if successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    //deltes product already in the table
    public int deleteProduct(String productId)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM product where prod_id = ?");
            //set parameters of query here but using the values for the product object
            ps.setString(1, productId);
            status = ps.executeUpdate();  //if successful status should return 1

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

}//class
