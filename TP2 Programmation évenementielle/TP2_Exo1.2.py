import sys
import urllib.request
from datetime import datetime
from PyQt5.QtWidgets import *
from PyQt5.QtGui import *
from PyQt5.QtCore import *


class myMainWindow(QWidget):
    def __init__(self, parent=None):
        QWidget.__init__(self, parent=parent)

        # attributs de la fenetre principale
        self.setGeometry(300, 300, 800, 400)
        self.titlestart = "DECOUTY Hugo " + urllib.request.urlopen('http://bot.whatismyipaddress.com/').read().decode(
            'utf8') + datetime.now().strftime("  %H:%M:%S")
        self.setWindowTitle(self.titlestart)
        self.rectUtil = self.rect()
        self.eventPaintCounter = 0
        self.x = 0
        self.y = 0
        self.initUI()  # appel d'une methode dédiée à la création de l'IHM

    def dessine(self):
        self.doPaint = True
        self.update()

    def efface(self):
        self.doPaint = False
        self.update()

    def keyPressEvent(self, event):
        if event.key() == Qt.Key_D:
            self.dessine()
        if event.key() == Qt.Key_E:
            self.efface()

    def paintEvent(self, event):
        qp = QPainter()
        qp.begin(self)
        self.setWindowTitle(self.titlestart + " " + str(self.width()) + " " + str(self.height()))
        if self.doPaint:
            width, height = self.width(), self.height()
            self.rectUtil = self.rect()

            #Tracé de la ligne horizontale
            qp.drawLine(0, height/2, width, height/2)

            #Tracé de la ligne verticale
            nb_lignes = 12
            for i in range(1, nb_lignes+1):
                step = (width/(nb_lignes+1))*i
                qp.drawLine(step, height, step, 0)
        else:
            qp.eraseRect(self.rectUtil)

        qp.end()

    def mouseReleaseEvent(self, event):
        p = event.pos()

        self.x = event.x()
        self.y = event.y()

        self.update()

    def initUI(self):
        # L'affichage sur la console peut-être supprimé, il faudra completer ici pour la création d'autre elements IHM
        w = self.width()
        h = self.height()

        self.bu1 = QPushButton("Dessine", self)
        self.bu1.clicked.connect(self.dessine)
        self.bu1.setGeometry(10, 10, 50, 30)

        self.bu2 = QPushButton("Efface", self)
        self.bu2.clicked.connect(self.efface)
        self.bu2.setGeometry(10,50,50,30)
        self.doPaint = False






if __name__ == '__main__':
    app = QApplication(sys.argv)
    w = myMainWindow()
    w.show()
    app.exec_()  # ou de préférence sys.exit(app.exec_()) si vous êtes sous linux
