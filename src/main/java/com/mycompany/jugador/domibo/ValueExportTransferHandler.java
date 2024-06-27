/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jugador.domibo;

import javax.swing.*;
import java.awt.datatransfer.*;

/**
 *
 * @author Pc
 */
public class ValueExportTransferHandler extends TransferHandler {
    private JFicha ficha;

    public ValueExportTransferHandler(JFicha ficha) {
        this.ficha = ficha;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new FichaTransferable(ficha);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
}
