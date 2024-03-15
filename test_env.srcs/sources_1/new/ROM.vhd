----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/13/2023 03:38:02 PM
-- Design Name: 
-- Module Name: ROM - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ROM is
  Port ( 
   adresa :in STD_LOGIC_VECTOR (7 downto 0);
   rom_enable:in STD_LOGIC;
   clock:in STD_LOGIC;
   data_output:out STD_LOGIC_VECTOR(15 downto 0));
end ROM;

architecture Behavioral of ROM is
type rom_type is array (0 to 255) of STD_LOGIC_VECTOR(15 downto 0);
signal mem:rom_type :=(
x"0001",
x"0005",
x"000A",
x"0F09",
x"FF10",
others => x"0000");
begin
process(clock) is
begin
if rising_edge(clock) then
if rom_enable='1' then
data_output<=mem(conv_integer(unsigned(adresa)));
end if;
end if;
end process;

end Behavioral;
