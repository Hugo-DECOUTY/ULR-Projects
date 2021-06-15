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
        self.setGeometry(300,300,800,400)
        self.titlestart = "DECOUTY Hugo " + urllib.request.urlopen('http://bot.whatismyipaddress.com/').read().decode('utf8') + datetime.now().strftime("  %H:%M:%S")
        self.setWindowTitle(self.titlestart) 
        self.rectUtil = self.rect()
        self.eventPaintCounter = 0
        self.x = 0
        self.y = 0



        self.initUI()   # appel d'une methode dédiée à la création de l'IHM

    def paintEvent(self, event):
        qp = QPainter()
        qp.begin(self)
        self.rectUtil = self.rect()
        self.eventPaintCounter = self.eventPaintCounter + 1
        qp.drawText(self.rectUtil, Qt.AlignCenter, "Paint event N° " + str(self.eventPaintCounter)
                    + "\nClic de souris à " + str(self.x) + "," + str(self.y))
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
        print(str(w)+","+str(h))

if __name__ == '__main__':
    app = QApplication(sys.argv)
    w = myMainWindow() 
    w.show() 
    app.exec_()    # ou de préférence sys.exit(app.exec_()) si vous êtes sous linux
