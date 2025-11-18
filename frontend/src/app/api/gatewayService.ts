const gatewayServiceUrl = process.env.NEXT_PUBLIC_GATEWAY_SERVICE_URL;

export async function fetchGatewayData() {
  const response = await fetch(`${gatewayServiceUrl}/gateway-data`);
  if (!response.ok) {
    throw new Error('Failed to fetch gateway data');
  }
  return response.json();
}