package ru.ncedu.java.SolomatinAA.TreeNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Artem Solomatin on 19.03.17.
 * NetCracker
 */
public class TreeNodeImpl implements TreeNode {
    private List<TreeNode> childs;
    private TreeNode parent;
    private Object data;
    private boolean expanded;


    TreeNodeImpl(){

    }
    /**
     * Returns the parent <code>TreeNode</code>.
     */
    @Override
    public TreeNode getParent() {
        return parent;
    }

    /**
     * Sets the parent <code>TreeNode</code>.<br/>
     * Is typically called in {@link #addChild(TreeNode)} and {@link #removeChild(TreeNode)} methods
     * of the parent <code>TreeNode</code> itself.
     *
     * @param parent
     */
    @Override
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * Returns the top of the tree that contains this <code>TreeNode</code>.
     *
     * @return root node or null if the parent of this node is null (i.e. the returned node != this).
     */
    @Override
    public TreeNode getRoot() {
        TreeNode currentRoot = this;

        while(currentRoot.getParent() !=null ) {
            currentRoot = currentRoot.getParent();
        }

        return currentRoot == this? null : currentRoot;
    }

    /**
     * Returns false if this <code>TreeNode</code> has non-zero number of children.
     *
     * @return true if the node is a leaf (i.e. does not have child nodes)
     */
    @Override
    public boolean isLeaf() {
        return childs == null || childs.isEmpty();
    }

    /**
     * Returns the number of children which this <code>TreeNode</code> has.
     */
    @Override
    public int getChildCount() {
        return childs.size();
    }

    /**
     * Returns children <code>TreeNode</code>'s as {@link Iterator}.
     */
    @Override
    public Iterator<TreeNode> getChildrenIterator() {
        return childs.iterator();
    }

    /**
     * Adds the given child to this <code>TreeNode</code> and sets this as the parent to it.
     *
     * @param child
     */
    @Override
    public void addChild(TreeNode child) {
        if(childs != null) {
            childs.add(child);
        }else{
            childs = new ArrayList<>();
            childs.add(child);
        }
        child.setParent(this);
    }

    /**
     * Removes the given child from this <code>TreeNode</code> and (if succeeded)
     * sets null as the parent to it (in order to leave the tree in the consistent state).
     *
     * @param child
     * @return true if succeeded, false if the child was not found
     */
    @Override
    public boolean removeChild(TreeNode child) {
        if(child == null){
            return false;
        }
        boolean status = childs.remove(child);
        child.setParent(null);
        return status;
    }

    /**
     * Returns the "expanded" state of this <code>TreeNode</code>.<br/>
     * By default (unless {@link #setExpanded(boolean)} is called)
     * "expanded" is false (i.e. the node is "collapsed").
     */
    @Override
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * Sets the "expanded" state to this <code>TreeNode</code> and to all its children, recursively
     *
     * @param expanded true - to expand this tree branch, false - to collapse this tree branch.
     */
    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        for(TreeNode x: childs)
            x.setExpanded(expanded);
    }

    /**
     * Returns the user object stored in this <code>TreeNode</code>
     * or <code>null</code> if {@link #setData(Object)} was not called.
     */
    @Override
    public Object getData() {
        return data;
    }

    /**
     * Sets the user data to store in this <code>TreeNode</code>.
     *
     * @param data
     */
    @Override
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Returns the string representation of the path from root to this <code>TreeNode</code>.<br/>
     * Path elements are separated by "->".<br/>
     * Each path element is either getData().toString() or "empty" if getData()==null.<br/>
     * For example: "rootNode0->node1->node13->empty" ("rootNode0" is a result of getRoot().getData().toString() in this example).
     */
    @Override
    public String getTreePath() {
        if(parent == null){
            return data == null? "empty" : data.toString();
        }else {
            return parent.getTreePath() + "->" +  (data == null? "empty" : data.toString());
        }
    }

    /**
     * Finds the (first) node with the given data among the parents' sequence of this <code>TreeNode</code>.
     * By convention, the parents' sequence includes this node itself (i.e. the following may be true: obj.findParent(*) == obj).<br/>
     * Data objects should be compared by equals() method (but if the given data is null then the parent with null data should be returned).
     *
     * @param data the data to find; may be <code>null</code>
     * @return the node found or <code>null</code> if no matching data was found.
     */
    @Override
    public TreeNode findParent(Object data) {
        if(data != null){
            if(data.equals(this.data)){
                return this;
            }else if(parent == null){
                return null;
            }else{
                return parent.findParent(data);
            }
        }else{
            if(this.data == null){
                return this;
            }else if(this.parent != null){
                return parent.findParent(data);
            }
            return null;
        }
    }

    /**
     * Finds the (first) node with the given data among the children of this <code>TreeNode</code>.<br/>
     * Searches it recursively (if some child doesn't have the given data, the children of this child are searched, and so on).<br/>
     * Data objects should be compared by equals() method (but if the given data is null then the child with null data should be returned).
     *
     * @param data the data to find; may be <code>null</code>
     * @return the node found or <code>null</code> if no matching data was found.
     */
    @Override
    public TreeNode findChild(Object data) {
        if (childs == null)
            return null;
        for(TreeNode tmp: childs){
            if ( data != null && data.equals(tmp.getData()))
                return tmp;
            if ( data == null && tmp.getData() == null){
                return tmp;
            }
        }
        for(TreeNode tmp: childs)
            return tmp.findChild(data);
        return null;
        /*  //НЕ РАБОТАЕТ С null
        if(this.data == data){
            return this;
        }
        if (childs.isEmpty()) {
            return null;
        }
        for(TreeNode x: childs){
            if ( data != null && data.equals(x.getData()))
                return x;
            if ( data == null && x.getData() == null){
                return x;
            }
        }
        for(TreeNode tmp: childs) {
            return tmp.findChild(data);
        }
        return null;
         */
    }
}
