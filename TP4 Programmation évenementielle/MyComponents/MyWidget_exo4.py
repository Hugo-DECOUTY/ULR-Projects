# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ComposantTP4_partie3.ui'
#
# Created by: PyQt5 UI code generator 5.9.2
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_Form(object):
    def setupUi(self, Form):
        Form.setObjectName("Form")
        Form.resize(1196, 677)
        self.horizontalLayoutWidget = QtWidgets.QWidget(Form)
        self.horizontalLayoutWidget.setGeometry(QtCore.QRect(10, 110, 1181, 361))
        self.horizontalLayoutWidget.setObjectName("horizontalLayoutWidget")
        self.horizontalLayout = QtWidgets.QHBoxLayout(self.horizontalLayoutWidget)
        self.horizontalLayout.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout.setObjectName("horizontalLayout")
        self.mLabel1 = QtWidgets.QLabel(self.horizontalLayoutWidget)
        self.mLabel1.setAutoFillBackground(True)
        self.mLabel1.setText("")
        self.mLabel1.setObjectName("mLabel1")
        self.horizontalLayout.addWidget(self.mLabel1)
        self.mLabel2 = QtWidgets.QLabel(self.horizontalLayoutWidget)
        self.mLabel2.setAutoFillBackground(True)
        self.mLabel2.setText("")
        self.mLabel2.setObjectName("mLabel2")
        self.horizontalLayout.addWidget(self.mLabel2)
        self.mLabel3 = QtWidgets.QLabel(self.horizontalLayoutWidget)
        self.mLabel3.setAutoFillBackground(True)
        self.mLabel3.setText("")
        self.mLabel3.setObjectName("mLabel3")
        self.horizontalLayout.addWidget(self.mLabel3)
        self.lineEdit = QtWidgets.QLineEdit(Form)
        self.lineEdit.setGeometry(QtCore.QRect(200, 540, 721, 21))
        self.lineEdit.setAlignment(QtCore.Qt.AlignCenter)
        self.lineEdit.setReadOnly(True)
        self.lineEdit.setPlaceholderText("")
        self.lineEdit.setObjectName("lineEdit")

        self.retranslateUi(Form)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        _translate = QtCore.QCoreApplication.translate
        Form.setWindowTitle(_translate("Form", "Form"))
        self.lineEdit.setText(_translate("Form", "Lancer la loterie en pressant \'S\'"))

