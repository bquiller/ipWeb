package org.cocktail.ipweb.serveur.metier;

// _IpDiplSansRn.java
// 
// Created by eogenerator
// DO NOT EDIT.  Make changes to IpDiplSansRn.java instead.


import com.webobjects.eocontrol.EOGenericRecord;

@SuppressWarnings("serial")
public abstract class _IpUeFermee extends EOGenericRecord {

    public _IpUeFermee() {
        super();
    }

/*
    // If you add instance variables to store property values you
    // should add empty implementions of the Serialization methods
    // to avoid unnecessary overhead (the properties will be
    // serialized for you in the superclass).
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    }
*/

    public Number fannKey() {
        return (Number)storedValueForKey("fannKey");
    }

    public void setFannKey(Number aValue) {
        takeStoredValueForKey(aValue, "fannKey");
    }

    public Number mueKey() {
        return (Number)storedValueForKey("mueKey");
    }

    public void setMueKey(Number aValue) {
        takeStoredValueForKey(aValue, "mueKey");
    }
}