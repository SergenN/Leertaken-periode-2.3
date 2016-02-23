package com.github.leertaken.leertaak2.Opdracht2;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

public class TreeClass {
    private DefaultMutableTreeNode persoon,sales_rep, employee,engineer,customer,
            us_customer, local_customers, regional_customers,
            non_us_customer;

    public TreeClass(){
        persoon=            new DefaultMutableTreeNode("Person              1|Root");
        employee =          new DefaultMutableTreeNode("Employee            2|L");
        customer =          new DefaultMutableTreeNode("Customer            3|R");
        persoon.add(employee);
        persoon.add(customer);

        sales_rep =         new DefaultMutableTreeNode("Sales_Rep           4|LL");
        engineer =          new DefaultMutableTreeNode("Engineer            5|LR");
        employee.add(sales_rep);
        employee.add(engineer);

        us_customer =       new DefaultMutableTreeNode("US_Customer         6|RL");
        non_us_customer=    new DefaultMutableTreeNode("Non_US_Customer     7|RR");
        customer.add(us_customer);
        customer.add(non_us_customer);

        local_customers =   new DefaultMutableTreeNode("Local_Customer      8|RLL");
        regional_customers =new DefaultMutableTreeNode("Regional_Customer   9|RLR");
        us_customer.add(local_customers);
        us_customer.add(regional_customers);

    }


    public void printBreedteOrdering() {
        Enumeration person = persoon.breadthFirstEnumeration();
        System.out.println("Printing Breedte ordering");
        while (person.hasMoreElements()) {
            System.out.println(person.nextElement());
        }
    }

    public void printPreOrdering(){
        Enumeration person = persoon.preorderEnumeration();
        System.out.println("Printing PreOrdering");
        while (person.hasMoreElements()){
            System.out.println(person.nextElement());
        }
    }

    public void printPostOrdering(){
        Enumeration person = persoon.postorderEnumeration();
        System.out.println("Printing PreOrdering");
        while (person.hasMoreElements()){
            System.out.println(person.nextElement());
        }
    }

}
