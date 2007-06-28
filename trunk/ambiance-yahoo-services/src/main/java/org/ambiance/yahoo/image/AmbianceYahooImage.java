package org.ambiance.yahoo.image;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.bind.JAXBException;

import org.ambiance.transport.TransporterException;
import org.ambiance.yahoo.AmbianceYahooException;
import org.ambiance.yahoo.QueryYahooService;
import org.ambiance.yahoo.image.ResultSet;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;

/**
 * @plexus.component role="org.ambiance.yahoo.AmbianceYahooService" role-hint="image"
 */
public class AmbianceYahooImage extends QueryYahooService implements Initializable {

	private static String BASE_URL = "http://search.yahooapis.com/ImageSearchService/V1/imageSearch?appid=";

	public Object query(String query) throws AmbianceYahooException {

		return query(query, new ImageRequestParameter());

	}

	public ResultSet query(String query, ImageRequestParameter parameters) throws AmbianceYahooException {
		try {

			String requesturl = new StringBuilder(BASE_URL).append(yahooId).append("&query=").append(
					URLEncoder.encode(query, "UTF-8")).append(parameters.toString()).toString();

			return (ResultSet) u.unmarshal(transporter.getAsStream(requesturl));

		} catch (TransporterException te) {

			getLogger().error("Error while getting resutl from yahoo");
			throw new AmbianceYahooException(te);

		} catch (JAXBException je) {

			getLogger().error("Unable to unmarshall");
			throw new AmbianceYahooException(je);

		} catch (UnsupportedEncodingException uee) {

			getLogger().error("Error while encoding the query");
			throw new AmbianceYahooException(uee);

		}
	}

}
