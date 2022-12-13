/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankUI.components.account;

import bankBackend.entity.enums.AccountState;

import javax.swing.*;

/**
 * @author NathanY
 */
public class AccountStateData {
    public static DefaultComboBoxModel model = new DefaultComboBoxModel<>(AccountState.values());
}
