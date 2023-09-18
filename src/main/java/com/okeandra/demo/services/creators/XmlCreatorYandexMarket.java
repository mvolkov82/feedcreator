package com.okeandra.demo.services.creators;

import com.okeandra.demo.models.Offer;
import com.okeandra.demo.models.OfferYandex;
import com.okeandra.demo.models.YmlObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Component
@Qualifier("xmlYandexMarket")
public class XmlCreatorYandexMarket extends XmlCreator {
    @Override
    public String getBodyAsXML(YmlObject ymlObject) {
        Document document = getDocument(ymlObject);
        Element root = document.createElement("offers");
        document.appendChild(root);

        for (Offer element : ymlObject.getBody()) {
            OfferYandex offer = (OfferYandex) element;
            Element blockOffer = getBlockOffer(document, root, offer);
            addStringElement(document, blockOffer, "url", offer::getUrl);
            addDoubleElement(document, blockOffer, "price", offer::getPrice);
            addIntElement(document, blockOffer, "count", offer::getInStock);
            addStringElement(document, blockOffer, "currencyId", offer::getCurrencyId);
            addStringElement(document, blockOffer, "categoryId", offer::getCategoryId);

            offer.getAdditionalPictures().forEach(pic -> addStringElement(document, blockOffer, "picture", pic));

            addBooleanElement(document, blockOffer, "store", offer::getStore);
            addBooleanElement(document, blockOffer, "pickup", offer::getPickup);
            addBooleanElement(document, blockOffer, "delivery", offer::getPickup);
            addStringElement(document, blockOffer, "vendor", offer::getVendor);
            addStringElement(document, blockOffer, "vendorCode", offer::getVendorCode);
            addStringElement(document, blockOffer, "barcode", offer::getBarcode);
            addStringElement(document, blockOffer, "model", offer::getModel);
            addStringElement(document, blockOffer, "description", offer::getDescription);
            addStringElement(document, blockOffer, "dimensions", offer::getDimensions);
            addIntElementWithoutZero(document, blockOffer, "weight", offer::getWeight);
            addStringElement(document, blockOffer, "country_of_origin", offer::getCountry_of_origin);


            //<shipment-options> <option days="1" order-before="15:00"/> </shipment-options>
            Element deliveryOptions = document.createElement("delivery-options");
            blockOffer.appendChild(deliveryOptions);
            Element shipmentOptionElement = document.createElement("option");
            shipmentOptionElement.setAttribute("cost", "1");
            shipmentOptionElement.setAttribute("order-before", "7");
            shipmentOptionElement.setAttribute("days", String.valueOf(offer.getDays()));
            deliveryOptions.appendChild(shipmentOptionElement);
        }

        return transformBodyToString(document);
    }

    @Override
    public Element getBlockOffer(Document document, Element root, Offer offer) {
        Element blockOffer = document.createElement("offer");
        blockOffer.setAttribute("type", "vendor.model");
        blockOffer.setAttribute("available", String.valueOf(((OfferYandex) offer).isAvailable()));
//        blockOffer.setAttribute("id", String.valueOf(offer.getId()));
        blockOffer.setAttribute("id", String.valueOf(offer.getVendorCode()));
        root.appendChild(blockOffer);
        return blockOffer;
    }

}
