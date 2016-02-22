package com.github.leertaken.leertaak2.opdracht1;

import sun.reflect.generics.tree.Tree;

import java.util.Stack;

/**
 * Created by Sergen Nurel
 * Date of creation 10-2-2016, 15:42
 * |
 * Authors: Sergen Nurel,
 * |
 * Version: 1.0
 * Package: com.github.leertaken
 * Class:
 * Description:
 * |
 * |
 * Changelog:
 * 1.0:
 */
public class Main {
    public static void main(String[] args){
        Main mainClass = new Main();
        TreeNode<String> tree = new TreeNode<>("F");
        tree.left = new TreeNode<>("B");
        tree.right = new TreeNode<>("G");

        tree.left.left = new TreeNode<>("A");
        tree.left.right = new TreeNode<>("D");

        tree.left.right.left = new TreeNode<>("C");
        tree.left.right.right = new TreeNode<>("E");

        tree.right.right = new TreeNode<>("I");
        tree.right.right.left = new TreeNode<>("H");

        System.out.println("In order:");
        mainClass.inOrder(tree);
        System.out.println("\n\nPre-order:");
        mainClass.preOrder(tree);
        System.out.println("\n\nPost-order:");
        mainClass.postOrder(tree);
        System.out.println("\n\nVoor controle: https://en.wikipedia.org/wiki/Tree_traversal");

    }

    public void inOrder(TreeNode root){

        TreeNode current = root;
        Stack<TreeNode> stack = new Stack();

        System.out.println();

        while (current != null || !stack.isEmpty()){
            if (current != null){
                stack.push(current);
                current = current.left;
            }
            else{
                TreeNode previous = stack.pop();
                System.out.print(previous.element + " ");
                current = previous.right;
            }
        }
    }

    public void preOrder(TreeNode root){
        TreeNode current = root;
        Stack<TreeNode> stack = new Stack<>();

        while ( current != null || !stack.isEmpty() ) {
            if (current != null){
                stack.push(current);
                System.out.print(current.element + " ");
                current = current.left;
            }
            else{
                TreeNode previous = stack.pop();
                current = previous.right;
            }
        }
    }

    public void postOrder(TreeNode root){
        TreeNode current = root;
        Stack<TreeNode> stack = new Stack<>();
        while ( current != null || !stack.isEmpty() )
        if ( current != null){
            stack.push(current);
            current = current.left;
        }
        else {
            current = stack.pop();
            if (current.secondPop){
                System.out.print(current.element + " ");
                current = null;
            }
            else{
                current.secondPop = true;
                stack.push(current);
                current = current.right;
            }
        }
    }
}

class TreeNode<E> {
    E element;
    TreeNode<E> left;
    TreeNode<E> right;
    boolean secondPop;

    public TreeNode(E e) {
        element = e;
    }
}