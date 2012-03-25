/*
    This file is part of RouteConverter.

    RouteConverter is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    RouteConverter is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with RouteConverter; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Copyright (C) 2007 Christian Pesch. All Rights Reserved.
*/

package slash.navigation.converter.gui.undo;

import slash.navigation.catalog.domain.Catalog;
import slash.navigation.catalog.model.CategoryTreeModel;
import slash.navigation.catalog.model.CategoryTreeNode;
import slash.navigation.catalog.model.RouteModel;
import slash.navigation.catalog.model.RoutesTableModel;
import slash.navigation.converter.gui.helper.RouteServiceOperator;
import slash.navigation.converter.gui.models.CatalogModel;
import slash.navigation.converter.gui.models.CatalogModelImpl;
import slash.navigation.gui.UndoManager;

import javax.swing.tree.TreeModel;
import java.util.List;

import static slash.navigation.converter.gui.helper.JTreeHelper.asNames;
import static slash.navigation.converter.gui.helper.JTreeHelper.asParents;

/**
 * Acts as a {@link TreeModel} for the categories and routes of a {@link Catalog}.
 *
 * @author Christian Pesch
 */

public class UndoCatalogModel implements CatalogModel {
    private final CatalogModelImpl delegate;
    private final UndoManager undoManager;

    public UndoCatalogModel(UndoManager undoManager, CategoryTreeNode root, RouteServiceOperator operator) {
        this.delegate = new CatalogModelImpl(root, operator);
        this.undoManager = undoManager;
    }

    public CategoryTreeModel getCategoryTreeModel() {
        return delegate.getCategoryTreeModel();
    }

    public RoutesTableModel getRoutesTableModel() {
        return delegate.getRoutesTableModel();
    }

    public void add(List<CategoryTreeNode> parents, List<String> names, Runnable invokeLaterRunnable) {
        add(parents, names, invokeLaterRunnable, true);
    }
    
    void add(List<CategoryTreeNode> categories, List<String> names, Runnable invokeLaterRunnable, boolean trackUndo) {
        delegate.add(categories, names, invokeLaterRunnable);
        if (trackUndo)
            undoManager.addEdit(new AddCategories(this, categories, names));
    }

    public void rename(CategoryTreeNode category, String name) {
        rename(category, name, true);
    }

    void rename(CategoryTreeNode category, String newName, boolean trackUndo) {
        String oldName = category.getName();
        delegate.rename(category, newName);
        if (trackUndo)
            undoManager.addEdit(new RenameCategory(this, category, oldName, newName));
    }

    public void move(List<CategoryTreeNode> categories, CategoryTreeNode parent) {
        move(categories, asParents(parent, categories.size()));
    }

    public void move(List<CategoryTreeNode> categories, List<CategoryTreeNode> parents) {
        move(categories, parents, true);
    }
    
    void move(List<CategoryTreeNode> categories, List<CategoryTreeNode> parents, boolean trackUndo) {
        List<CategoryTreeNode> oldParents = asParents(categories);
        delegate.move(categories, parents);
        if (trackUndo)
            undoManager.addEdit(new MoveCategories(this, categories, oldParents, parents));
    }

    public void remove(List<CategoryTreeNode> categories) {
        remove(asParents(categories), asNames(categories));
    }

    public void remove(List<CategoryTreeNode> parents, List<String> names) {
        remove(parents, names, true);
    }

    void remove(List<CategoryTreeNode> categories, List<String> names, boolean trackUndo) {
        delegate.remove(categories, names);
        if (trackUndo)
            undoManager.addEdit(new RemoveCategories(this, categories, names));
    }

    public void rename(RouteModel route, String name) {
        rename(route, name, true);
    }

    void rename(RouteModel route, String newName, boolean trackUndo) {
        String oldName = route.getName();
        delegate.rename(route, newName);
        if (trackUndo)
            undoManager.addEdit(new RenameRoute(this, route, oldName, newName));

    }
}
