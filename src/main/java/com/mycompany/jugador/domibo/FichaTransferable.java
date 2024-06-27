/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import java.awt.datatransfer.*;

/**
 *
 * @author Pc
 */
public class FichaTransferable implements Transferable {
    public static final DataFlavor FICHA_FLAVOR = new DataFlavor(JFicha.class, "Ficha");
    private JFicha ficha;

    public FichaTransferable(JFicha ficha) {
        this.ficha = ficha;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { FICHA_FLAVOR };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(FICHA_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(FICHA_FLAVOR)) {
            return ficha;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
